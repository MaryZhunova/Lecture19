package com.example.recipe.presentation.switchfavourites.addrecipe

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.NetworkApp
import com.example.recipe.databinding.AddRecipeBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.addrecipe.viewmodel.AddRecipeViewModel
import javax.inject.Inject


class AddRecipeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var addRecipeViewModel: AddRecipeViewModel
    private lateinit var binding: AddRecipeBinding
    private var uri: String = ""
    private lateinit var gallery: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gallery = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.image.setImageURI(it)
                uri = it.toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkApp.appComponent(requireContext()).inject(this)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        binding.done.setOnClickListener {
            if (binding.name.text != null) {
                val recipe = MyRecipePresentationModel(binding.name.text.toString(), binding.ingredients.text.toString(), binding.recipeDescription.text.toString(), uri)
                addRecipeViewModel.addToMyRecipes(recipe)
            }
            hideKeyboard()
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.chooseImage.setOnClickListener {
            gallery.launch("image/*")
        }

    }

    private fun createViewModel() {
        addRecipeViewModel = ViewModelProvider(this, viewModelFactory)[AddRecipeViewModel::class.java]
    }

    private fun observeLiveData() {
        addRecipeViewModel.getErrorLiveData()
            .observe(viewLifecycleOwner) { throwable: Throwable -> showError(throwable) }
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), "Couldn't add the recipe. ${throwable.message}", Toast.LENGTH_SHORT).show()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        fun newInstance() = AddRecipeFragment()
    }
}

