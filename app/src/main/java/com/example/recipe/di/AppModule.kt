package com.example.recipe.di

import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.domain.BaseRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.RecipeRToRecipeDConverter
import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.domain.RecipeD
import dagger.Binds
import dagger.Module

@Module(includes = [ConfigModule::class])
interface AppModule {

    @Binds
    fun provideBaseRepository(okhttpRepository: OkhttpRepository): BaseRepository
    @Binds
    fun provideRecipesApi(okHttpRecipesApiImpl: OkHttpRecipesApiImpl): RecipesApi
    @Binds
    fun provideConverter(recipeRToRecipeDConverter: RecipeRToRecipeDConverter): Converter<RecipeR, RecipeD>

}
