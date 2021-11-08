package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.db.RecipesDbImpl
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.data.Recipe
import com.example.recipe.models.data.RecipeDB
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Реализация [RecipesRepository]
 *
 * @param recipesApi апи для работы с сервераными данными
 */

class RecipesRepositoryImpl @Inject constructor(private val recipesDbImpl: RecipesDbImpl, private val recipesApi: RecipesApi, private val converterApi: Converter<Recipe, RecipeDomainModel>, private val converterDb: Converter<RecipeDB, RecipeDomainModel>): RecipesRepository {

    override fun get(query: String): List<RecipeDomainModel> {
        return recipesApi.get(query).map(converterApi::convert)
    }

    override fun getFavouriteRecipes(): List<RecipeDomainModel> {
        return recipesDbImpl.getFavouriteRecipes().map(converterDb::convert)
    }
}