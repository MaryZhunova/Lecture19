package com.example.recipe.view.recipedetail

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.R
import com.example.recipe.data.model.Recipe
import com.example.recipe.databinding.RecipeDetailBinding
import android.text.Html

import android.text.method.LinkMovementMethod
import com.example.recipe.utils.GlideApp

/**
 * Активити приложения, которая отображает детальную информацию об одном рецепте
 */
class RecipeDetailActivity: AppCompatActivity() {

    private lateinit var binding: RecipeDetailBinding
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("recipe")

        //Отобразить картинку блюда
        val tempUri = Uri.parse(recipe?.image)
        GlideApp.with(applicationContext)
            .asBitmap()
            .load(tempUri)
            .into(binding.image);

        //Отобразить название рецепта
        binding.label.text = recipe?.label

        //Отобразить список ингредиентов
        val adapter = recipe?.let { ArrayAdapter(this, R.layout.ingredient, R.id.ingredient_name, it.ingredientLines) }
        binding.ingredientsList.adapter = adapter


        //Отобразить ссылку на подробный рецепт
        binding.url.isClickable = true
        binding.url.movementMethod = LinkMovementMethod.getInstance()
        val text = "<a href='${recipe?.url}'> ${recipe?.source} </a>"
        binding.url.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);

    }
}