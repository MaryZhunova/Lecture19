package com.example.recipe.di

import com.example.recipe.presentation.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.recipedetail.RecipeDetailViewModel
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun getRecipesInfoViewModel(): RecipesInfoViewModel
    fun getFavouritesViewModel(): FavouritesViewModel
    fun getRecipeDetailViewModel(): RecipeDetailViewModel
}

