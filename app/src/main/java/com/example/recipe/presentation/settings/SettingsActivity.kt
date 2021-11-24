package com.example.recipe.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.R
import com.example.recipe.presentation.recipesinfo.RecipesInfoActivity

/**
 * Активити, которая отображает настройки
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

}