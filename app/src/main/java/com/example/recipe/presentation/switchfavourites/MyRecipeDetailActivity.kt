package com.example.recipe.presentation.switchfavourites

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.recipe.R
import com.example.recipe.databinding.MyRecipeDetailBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.utils.GlideApp

class MyRecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: MyRecipeDetailBinding
    private var recipe: MyRecipePresentationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyRecipeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recipe = intent.getParcelableExtra(RECIPE)

        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("switch_images", true)
        ) {
            val tempUri = Uri.parse(recipe?.image)
            GlideApp.with(this)
                    .asBitmap()
                    .load(tempUri)
                    .error(R.drawable.no_image_logo)
                    .into(binding.image)
        }

        binding.name.text = recipe?.recipeName ?: ""
        binding.ingredients.text = recipe?.ingredients ?: ""
        binding.directions.text = recipe?.instructions ?: ""
    }

    companion object {
        private const val RECIPE = "recipe"
        fun newIntent(context: Context, recipe: MyRecipePresentationModel): Intent {
            val intent = Intent(context, MyRecipeDetailActivity::class.java)
            intent.putExtra(RECIPE, recipe)
            return intent
        }
    }
}