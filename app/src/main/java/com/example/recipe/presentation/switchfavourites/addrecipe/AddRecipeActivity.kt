package com.example.recipe.presentation.switchfavourites.addrecipe

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.NetworkApp
import com.example.recipe.databinding.AddRecipeBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.addrecipe.viewmodel.AddRecipeViewModel
import javax.inject.Inject
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider.getUriForFile
import com.example.recipe.R
import java.io.File
import java.util.UUID




class AddRecipeActivity : AppCompatActivity(), ChooseImageFragment.OnItemClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var addRecipeViewModel: AddRecipeViewModel
    private lateinit var binding: AddRecipeBinding
    private var chooseImageFragment: ChooseImageFragment? = null
    private var uri: String = ""
    private var currentImageUri: Uri? = null
    private lateinit var takePicture: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            currentImageUri = savedInstanceState.getParcelable(URI)
            uri = savedInstanceState.getString(URI_STR, "")
            if (currentImageUri != null) {
                binding.image.setImageURI(currentImageUri)
            } else {
                binding.image.setImageURI(Uri.parse(uri))
            }
        }

        takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                binding.image.setImageURI(currentImageUri)
                uri = currentImageUri.toString()
            }
        }

        NetworkApp.appComponent(this).inject(this)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        binding.chooseImage.setOnClickListener {
            createFragment()
        }

        binding.takeImage.setOnClickListener {
            if (checkPermission()) {
                takePicture()
            } else {
                requestPermission()
                takePicture()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(URI, currentImageUri)
        outState.putString(URI_STR, uri)
    }

    private fun createFragment() {
        chooseImageFragment = ChooseImageFragment.newInstance()
        this.supportFragmentManager.beginTransaction()
            .add(R.id.container, chooseImageFragment!!)
            .addToBackStack("fragment")
            .commit()
    }

    private fun takePicture() {
        currentImageUri = getUriForFile(this, "com.example.recipe.provider",
            createImageFile())
        takePicture.launch(currentImageUri)
    }

    private fun createImageFile(): File {
        val uuid = UUID.randomUUID().toString()

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile( uuid + "image", ".jpg", storageDir)
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Can't access camera", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
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

    override fun onClick(uri: Uri) {
        binding.image.setImageURI(uri)
        this.uri = uri.toString()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 11
        const val URI = "uri"
        const val URI_STR = "uri"
        fun newIntent(context: Context) = Intent(context, AddRecipeActivity::class.java)
    }
}