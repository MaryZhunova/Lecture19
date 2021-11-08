package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.dao.RecipesDb
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

class RecipesRepositoryImpl @Inject constructor(private val recipesDbImpl: RecipesDb,
                                                private val recipesApi: RecipesApi,
                                                private val converter: Converter<Recipe, RecipeDB>,
                                                private val converterFromDb: Converter<RecipeDB, RecipeDomainModel>,
                                                private val converterToDb: Converter<RecipeDomainModel, RecipeDB>): RecipesRepository {

    override fun get(query: String): List<RecipeDomainModel> {
        val recipes = recipesApi.get(query).map(converter::convert)
        for (recipe in recipes) {
            if (recipesDbImpl.isFavourite(recipe)) {
                recipe.isFavourite = true
            }
        }
        return recipes.map(converterFromDb::convert)

    }

    override fun getFavouriteRecipes(): List<RecipeDomainModel> {
        return recipesDbImpl.getFavouriteRecipes().map(converterFromDb::convert)
    }

    override fun addToFavourites(recipe: RecipeDomainModel) {
        recipesDbImpl.addToFavourites(converterToDb.convert(recipe))
    }

    override fun deleteFromFavourites(recipe: RecipeDomainModel) {
        recipesDbImpl.deleteFromFavourites(converterToDb.convert(recipe))
    }
}