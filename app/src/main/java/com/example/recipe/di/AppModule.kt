package com.example.recipe.di

import android.database.sqlite.SQLiteOpenHelper
import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.db.RecipesDb
import com.example.recipe.data.db.RecipesDbHelper
import com.example.recipe.data.db.RecipesDbImpl
import com.example.recipe.data.repository.RecipesRepositoryImpl
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.converter.RecipeToDomainConverter
import com.example.recipe.models.data.Recipe
import com.example.recipe.models.data.RecipeDB
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun provideSQLiteOpenHelper(recipesDbHelper: RecipesDbHelper): SQLiteOpenHelper
    @Binds
    fun provideBaseRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository
    @Binds
    fun provideRecipesApi(okHttpRecipesApiImpl: OkHttpRecipesApiImpl): RecipesApi
    @Binds
    fun provideRecipesDb(recipesDb: RecipesDbImpl): RecipesDb
    @Binds
    fun provideConverter(recipeToDomainConverter: RecipeToDomainConverter): Converter<Recipe, RecipeDomainModel>
    @Binds
    fun provideConverter3(recipeDbToDomainConverter: DomainToPresentationConverter): Converter<RecipeDB, RecipeDomainModel>
    @Binds
    fun provideConverter2(domainToPresentationConverter: DomainToPresentationConverter): Converter<RecipeDomainModel, RecipePresentationModel>
    @Binds
    fun provideSchedulers(schedulersProvider: SchedulersProvider): ISchedulersProvider
}
