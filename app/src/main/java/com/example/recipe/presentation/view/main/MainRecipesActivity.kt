package com.example.recipe.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.databinding.ActivityMainBinding
import com.example.recipe.presentation.view.recipesinfo.RecipesInfoActivity

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

        //По клику на кнопку создать новую активити и передать в нее ключевое слово для поиска
        binding.goBtn.setOnClickListener {
            val newIntent = Intent(applicationContext, RecipesInfoActivity::class.java)
            newIntent.putExtra("query", binding.editQuery.text.toString())
            startActivity(newIntent)
        }
    }
}