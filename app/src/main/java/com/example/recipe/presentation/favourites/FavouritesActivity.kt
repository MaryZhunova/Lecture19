package com.example.recipe.presentation.favourites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.R

/**
 * Активити, которая отображает список избранных рецептов
 */
class FavouritesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourites_list)
    }

}