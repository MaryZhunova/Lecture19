package com.example.recipe.di

import com.example.recipe.presentation.switchfavourites.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel.MyRecipesViewModel
import com.example.recipe.presentation.recipedetail.viewmodel.RecipeDetailViewModel
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun getRecipesInfoViewModel(): RecipesInfoViewModel
    fun getFavouritesViewModel(): FavouritesViewModel
    fun getRecipeDetailViewModel(): RecipeDetailViewModel
    fun getMyRecipesViewModel(): MyRecipesViewModel
}

