package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.model.Recipe

/**
 * Реализация [BaseRepository]
 *
 * @param recipesApi апи для работы с сервераными данными
 */

class OkhttpRepository(private val recipesApi: RecipesApi): BaseRepository {

    override fun get(query: String): List<Recipe> {
        return recipesApi.get(query)
    }
}