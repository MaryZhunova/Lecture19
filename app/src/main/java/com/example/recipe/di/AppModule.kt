package com.example.recipe.di

import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.domain.BaseRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.RecipeToDomainConverter
import com.example.recipe.models.data.Recipe
import com.example.recipe.models.domain.RecipeDomainModel
import dagger.Binds
import dagger.Module

@Module(includes = [ConfigModule::class])
interface AppModule {

    @Binds
    fun provideBaseRepository(okhttpRepository: OkhttpRepository): BaseRepository
    @Binds
    fun provideRecipesApi(okHttpRecipesApiImpl: OkHttpRecipesApiImpl): RecipesApi
    @Binds
    fun provideConverter(recipeToDomainConverter: RecipeToDomainConverter): Converter<Recipe, RecipeDomainModel>

}
