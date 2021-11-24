package com.example.recipe.presentation.switchfavourites.addrecipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.AddRecipeBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.addrecipe.viewmodel.AddRecipeViewModel
import javax.inject.Inject

class AddRecipeActivity : AppCompatActivity(), ChooseImageFragment.OnItemClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var addRecipeViewModel: AddRecipeViewModel
    private lateinit var binding: AddRecipeBinding
    private var uri: String = ""
    private lateinit var gallery: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        gallery = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            ActivityResultCallback {
//                binding.image.setImageURI(it)
//                uri = it.toString()
//            })

        NetworkApp.appComponent(this).inject(this)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        binding.chooseImage.setOnClickListener {
//            gallery.launch("image/*")
            val viewGroup = FragmentContainerView(this)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            viewGroup.layoutParams = params
            viewGroup.id = R.id.fragment_id
            binding.root.addView(viewGroup)
            this.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_id, ChooseImageFragment.newInstance())
                .addToBackStack("fragment")
                .commit()
        }


    }

    private fun createViewModel() {
        addRecipeViewModel = ViewModelProvider(this, viewModelFactory)[AddRecipeViewModel::class.java]
    }

    private fun observeLiveData() {
        addRecipeViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, "Couldn't add the recipe. ${throwable.message}", Toast.LENGTH_SHORT).show()
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!(binding.name.text.isNullOrEmpty()  && binding.ingredients.text.isNullOrEmpty() && binding.recipeDescription.text.isNullOrEmpty())) {
            val recipe = MyRecipePresentationModel(binding.name.text.toString(), binding.ingredients.text.toString(), binding.recipeDescription.text.toString(), uri)
            addRecipeViewModel.addToMyRecipes(recipe)
        }
        onBackPressed()
        return true
    }
    companion object {
        fun newIntent(context: Context) = Intent(context, AddRecipeActivity::class.java)
    }

    override fun onClick(uri: Uri) {
        binding.image.setImageURI(uri)
        this.uri = uri.toString()
    }
}