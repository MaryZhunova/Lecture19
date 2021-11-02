package com.example.recipe.presentation.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.recipe.R
import com.example.recipe.databinding.ActivityMainBinding
import com.example.recipe.presentation.view.recipesinfo.RecipesInfoActivity
import com.example.recipe.presentation.view.settings.SettingsActivity

/**
 * Главная активити приложения, которая позволяет ввести ключевое слово
 * для поиска рецептов и отправить запрос
 */

class MainRecipesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Выбор темы приложения согласно заданным настройкам
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switch_theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //По клику на кнопку создать новую активити и передать в нее ключевое слово для поиска
        binding.goBtn.setOnClickListener {
            val newIntent = Intent(applicationContext, RecipesInfoActivity::class.java)
            newIntent.putExtra("query", binding.editQuery.text.toString())
            startActivity(newIntent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val newIntent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(newIntent)
        }
        return true
    }
}