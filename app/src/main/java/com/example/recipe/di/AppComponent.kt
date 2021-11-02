package com.example.recipe.di

import com.example.recipe.presentation.viewmodel.RecipesInfoViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getRecipesInfoViewModel(): RecipesInfoViewModel
}

