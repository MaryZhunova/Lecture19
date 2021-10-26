package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.domain.BaseRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.domain.RecipeD

/**
 * Реализация [BaseRepository]
 *
 * @param recipesApi апи для работы с сервераными данными
 */

class OkhttpRepository(private val recipesApi: RecipesApi, private val converter: Converter<RecipeR, RecipeD>): BaseRepository {

    override fun get(query: String): List<RecipeD>? {
        return recipesApi.get(query)?.map(converter::convert)
    }
}