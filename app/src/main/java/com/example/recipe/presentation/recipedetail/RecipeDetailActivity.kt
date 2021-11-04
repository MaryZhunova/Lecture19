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
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.GlideApp

/**
 * Активити приложения, которая отображает детальную информацию об одном рецепте
 */
class RecipeDetailActivity: AppCompatActivity() {

    private lateinit var binding: RecipeDetailBinding
    private var recipe: RecipePresentationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("recipe")

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

        //Обработать добавление/удаление рецепта в избранное
        binding.fav.setOnClickListener {
            if (!binding.favFilled.isVisible) {
                binding.favFilled.visibility = View.VISIBLE
                Toast.makeText(applicationContext, "Added to favourites", Toast.LENGTH_SHORT).show()
                //todo
            } else {
                binding.favFilled.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Removed from favourites", Toast.LENGTH_SHORT).show()
                //todo
            }
        }

        //Отобразить список ингредиентов
        val adapter = recipe?.let { ArrayAdapter(this, R.layout.ingredient, R.id.ingredient_name, it.ingredientLines) }
        binding.ingredientsList.adapter = adapter


        //Отобразить ссылку на подробный рецепт
        binding.url.isClickable = true
        binding.url.movementMethod = LinkMovementMethod.getInstance()
        val text = "<a href='${recipe?.url}'> ${recipe?.source} </a>"
        binding.url.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

    }
}