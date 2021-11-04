package com.example.recipe.di

import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun getRecipesInfoViewModel(): RecipesInfoViewModel
}

