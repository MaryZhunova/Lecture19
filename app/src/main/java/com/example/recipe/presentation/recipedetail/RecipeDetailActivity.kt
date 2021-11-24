package com.example.recipe.presentation.recipedetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.R
import com.example.recipe.databinding.RecipeDetailBinding
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.NetworkApp
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipedetail.viewmodel.RecipeDetailViewModel
import com.example.recipe.utils.GlideApp
import javax.inject.Inject

/**
 * Активити приложения, которая отображает детальную информацию об одном рецепте
 */
class RecipeDetailActivity : AppCompatActivity(), RecipeDetailView {

    private lateinit var binding: RecipeDetailBinding
    private lateinit var recipeDetailViewModel: RecipeDetailViewModel
    private var recipe: RecipePresentationModel? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NetworkApp.appComponent(applicationContext).inject(this)

        recipe = intent.getParcelableExtra(RECIPE)

        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        //Отобразить картинку блюда
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switch_images", true)) {
            val tempUri = Uri.parse(recipe?.image)
            GlideApp.with(applicationContext)
                .asBitmap()
                .load(tempUri)
                .error(R.drawable.no_image_logo)
                .into(binding.image)
        }
        //Отобразить название рецепта
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = recipe?.label
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
        recipeDetailViewModel = ViewModelProvider(this, viewModelFactory)[RecipeDetailViewModel::class.java]
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

    companion object {
        private const val RECIPE = "recipe"
        fun newIntent(context: Context, recipe: RecipePresentationModel): Intent {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(RECIPE, recipe)
            return intent
        }
    }
}