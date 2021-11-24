package com.example.recipe.presentation.switchfavourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.databinding.SwitchFavouritesBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.addrecipe.AddRecipeActivity
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Активити, которая отображает viewpager с фрагментами MyRecipes и Favourites
 */
class SwitchFavouritesActivity : AppCompatActivity(), MyRecipesFragment.OnItemClickListener {

    private lateinit var binding: SwitchFavouritesBinding
    private lateinit var adapter: SwitchFavouritesAdapter
    private val tabNames: Array<String> = arrayOf("My recipes", "Favourites")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SwitchFavouritesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = SwitchFavouritesAdapter(this)
        binding.pager.adapter = adapter

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
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