package com.example.recipe.presentation.switchfavourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.databinding.ActivitySwitchFavouritesBinding
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.example.recipe.presentation.addrecipe.AddRecipeActivity
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipeDetailActivity
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale

/**
 * Активити, которая отображает viewpager с фрагментами MyRecipes и Favourites
 */
class SwitchFavouritesActivity : AppCompatActivity(), MyRecipesFragment.OnItemClickListener {

    private lateinit var binding: ActivitySwitchFavouritesBinding
    private lateinit var adapter: SwitchFavouritesAdapter
    private var tabNames: Array<String> = arrayOf("Favourites", "My recipes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchFavouritesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = SwitchFavouritesAdapter(this)
        binding.pager.adapter = adapter

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        if (Locale.getDefault().language.equals("ru")) {
            tabNames = arrayOf("Избранное", "Мои рецепты")
        }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    override fun onClick(recipePresentationModel: MyRecipePresentationModel) {
        val newIntent = MyRecipeDetailActivity.newIntent(this, recipePresentationModel)
        startActivity(newIntent)
    }

    override fun addRecipe() {
        val newIntent = AddRecipeActivity.newIntent(this)
        startActivity(newIntent)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SwitchFavouritesActivity::class.java)
    }
}