package com.example.recipe.di

import com.example.recipe.domain.RecipesInteractor
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getRecipesInteractor(): RecipesInteractor
}

