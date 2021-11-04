package com.example.recipe.di

import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.domain.BaseRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.converter.RecipeToDomainConverter
import com.example.recipe.models.data.Recipe
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun provideBaseRepository(okhttpRepository: OkhttpRepository): BaseRepository
    @Binds
    fun provideRecipesApi(okHttpRecipesApiImpl: OkHttpRecipesApiImpl): RecipesApi
    @Binds
    fun provideConverter(recipeToDomainConverter: RecipeToDomainConverter): Converter<Recipe, RecipeDomainModel>
    @Binds
    fun provideConverter2(DomainToPresentationConverter: DomainToPresentationConverter): Converter<RecipeDomainModel, RecipePresentationModel>
    @Binds
    fun provideSchedulers(schedulersProvider: SchedulersProvider): ISchedulersProvider
}
