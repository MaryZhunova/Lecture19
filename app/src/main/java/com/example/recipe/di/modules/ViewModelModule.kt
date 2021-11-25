package com.example.recipe.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.presentation.recipedetail.viewmodel.RecipeDetailViewModel
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import com.example.recipe.presentation.addrecipe.viewmodel.AddRecipeViewModel
import com.example.recipe.presentation.switchfavourites.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel.MyRecipesViewModel
import com.example.recipe.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RecipesInfoViewModel::class)
    abstract fun bindsRecipesInfoViewModel(viewModel: RecipesInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel::class)
    abstract fun bindsRecipeDetailViewModel(viewModel: RecipeDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindsFavouritesViewModel(viewModel: FavouritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyRecipesViewModel::class)
    abstract fun bindsMyRecipesViewModel(viewModel: MyRecipesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddRecipeViewModel::class)
    abstract fun bindsAddRecipeViewModel(viewModel: AddRecipeViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}