package com.example.recipe.presentation.switchfavourites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.databinding.SwitchFavouritesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Активити, которая отображает viewpager с фрагментами MyRecipes и Favourites
 */
class SwitchFavouritesActivity : AppCompatActivity() {

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


}