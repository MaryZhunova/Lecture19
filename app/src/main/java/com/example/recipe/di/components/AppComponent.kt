package com.example.recipe.di.components

import com.example.recipe.di.modules.AppModule
import com.example.recipe.di.modules.NetworkModule
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import com.example.recipe.presentation.recipesinfo.RecipesInfoActivity
import com.example.recipe.presentation.addrecipe.AddRecipeActivity
import com.example.recipe.presentation.switchfavourites.favourites.FavouritesFragment
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: RecipeDetailActivity)
    fun inject(fragment: FavouritesFragment)
    fun inject(fragment: MyRecipesFragment)
    fun inject(activity: RecipesInfoActivity)
    fun inject(activity: AddRecipeActivity)
}

