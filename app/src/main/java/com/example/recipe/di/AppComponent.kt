package com.example.recipe.di

import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun getRecipesInfoViewModel(): RecipesInfoViewModel
}

