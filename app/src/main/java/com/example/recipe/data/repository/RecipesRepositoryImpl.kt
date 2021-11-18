package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.data.api.Recipe
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Реализация [RecipesRepository]
 *
 * @param recipesDao интерфейс для получения данных об избранных рецептах из базы данных
 * @param recipesApi апи для работы с сервераными данными
 * @param converter конвертер из RecipeModel в RecipeDomainModel
 * @param converterFromEntity из RecipeEntity в RecipeDomainModel
 * @param converterToEntity из RecipeDomainModel в RecipeEntity
 */
class RecipesRepositoryImpl @Inject constructor(private val recipesApi: RecipesApi,
                                                private val converter: Converter<Recipe, RecipeDomainModel>): RecipesRepository {

    override fun get(query: String): List<RecipeDomainModel> {
        return recipesApi.get(query).map(converter::convert)
    }
}