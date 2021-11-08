package com.example.recipe.di

import android.database.sqlite.SQLiteOpenHelper
import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.dao.RecipesDb
import com.example.recipe.data.dao.db.RecipesDbHelper
import com.example.recipe.data.dao.RecipesDbImpl
import com.example.recipe.data.repository.RecipesRepositoryImpl
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.converter.DomainToRecipeDBConverter
import com.example.recipe.models.converter.PresentationToDomainConverter
import com.example.recipe.models.converter.RecipeDBToDomainConverter
import com.example.recipe.models.converter.RecipeToRecipeDBConverter
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
    fun provideConverter(recipeToRecipeDBConverter: RecipeToRecipeDBConverter): Converter<Recipe, RecipeDB>
    @Binds
    fun provideConverter2(domainToPresentationConverter: DomainToPresentationConverter): Converter<RecipeDomainModel, RecipePresentationModel>
    @Binds
    fun provideConverter3(recipeDbToDomainConverter: RecipeDBToDomainConverter): Converter<RecipeDB, RecipeDomainModel>
    @Binds
    fun provideConverter4(domainToRecipeDbConverter: DomainToRecipeDBConverter): Converter<RecipeDomainModel, RecipeDB>
    @Binds
    fun provideConverter5(presentationToDomainConverter: PresentationToDomainConverter): Converter<RecipePresentationModel, RecipeDomainModel>
    @Binds
    fun provideSchedulers(schedulersProvider: SchedulersProvider): ISchedulersProvider
}
