package com.example.recipe.presentation.recipedetail

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.R
import com.example.recipe.databinding.RecipeDetailBinding
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.recipe.NetworkApp
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipedetail.viewmodel.RecipeDetailViewModel
import com.example.recipe.utils.GlideApp

/**
 * Активити приложения, которая отображает детальную информацию об одном рецепте
 */
class RecipeDetailActivity : AppCompatActivity(), RecipeDetailView {

    private lateinit var binding: RecipeDetailBinding
    private lateinit var recipeDetailViewModel: RecipeDetailViewModel
    private var recipe: RecipePresentationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("recipeModel")

        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        //Отобразить картинку блюда
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switch_images", true)) {
            val tempUri = Uri.parse(recipe?.image)
            GlideApp.with(applicationContext)
                .asBitmap()
                .load(tempUri)
                .into(binding.image)
        }

        //Отобразить название рецепта
        binding.label.text = recipe?.label

        //Отобразить избранный рецепт или нет
        if (recipe?.isFavourite == true) {
            binding.favFilled.visibility = View.VISIBLE
        } else {
            binding.favFilled.visibility = View.INVISIBLE
        }

//        binding.fav.setOnClickListener {
//            if (!binding.favFilled.isVisible) {
//                binding.favFilled.visibility = View.VISIBLE
//                recipeModel?.isFavourite = true
//                recipeModel?.let { it1 -> recipeDetailViewModel.addToFavourites(it1) }
//                Toast.makeText(applicationContext, "Added to favourites", Toast.LENGTH_SHORT).show()
//                //todo
//            } else {
//                binding.favFilled.visibility = View.INVISIBLE
//                recipeModel?.isFavourite = false
//                recipeModel?.let { it2 -> recipeDetailViewModel.deleteFromFavourites(it2) }
//                Toast.makeText(applicationContext, "Removed from favourites", Toast.LENGTH_SHORT)
//                    .show()
//                //todo
//            }
//        }

        //Отобразить список ингредиентов
        val adapter = recipe?.let {
            ArrayAdapter(
                this,
                R.layout.ingredient,
                R.id.ingredient_name,
                it.ingredientLines
            )
        }
        binding.ingredientsList.adapter = adapter


        //Отобразить ссылку на подробный рецепт
        binding.url.isClickable = true
        binding.url.movementMethod = LinkMovementMethod.getInstance()
        val text = "<a href='${recipe?.url}'> ${recipe?.source} </a>"
        binding.url.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun createViewModel() {
        recipeDetailViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
                    return NetworkApp.appComponent(applicationContext)
                        .getRecipeDetailViewModel() as T
                }
                throw IllegalArgumentException("UnknownViewModel")
            }
        })[RecipeDetailViewModel::class.java]
    }

    private fun observeLiveData() {
        recipeDetailViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.let {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }
}