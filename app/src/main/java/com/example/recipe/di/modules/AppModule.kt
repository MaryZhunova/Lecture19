package com.example.recipe.di.modules

import com.example.recipe.data.repository.RecipesRepositoryImpl
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.converter.DomainToRecipeEntityConverter
import com.example.recipe.models.converter.PresentationToDomainConverter
import com.example.recipe.models.converter.RecipeEntityToDomainConverter
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.models.converter.RecipeToDomainConverter
import com.example.recipe.models.data.api.Recipe
import com.example.recipe.models.data.api.RecipeResponse
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.domain.RecipeDomainModel2
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun provideBaseRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository

    @Binds
    fun provideConverter(toDomainConverter: RecipeToDomainConverter): Converter<RecipeResponse, RecipeDomainModel2>

    @Binds
    fun provideConverter2(domainToPresentationConverter: DomainToPresentationConverter): Converter<RecipeDomainModel, RecipePresentationModel>

    @Binds
    fun provideConverter3(recipeEntityToDomainConverter: RecipeEntityToDomainConverter): Converter<RecipeEntity, RecipeDomainModel>

    @Binds
    fun provideConverter4(domainToRecipeEntityConverter: DomainToRecipeEntityConverter): Converter<RecipeDomainModel, RecipeEntity>

    @Binds
    fun provideConverter5(presentationToDomainConverter: PresentationToDomainConverter): Converter<RecipePresentationModel, RecipeDomainModel>

    @Binds
    fun provideSchedulers(schedulersProvider: SchedulersProvider): ISchedulersProvider
}
