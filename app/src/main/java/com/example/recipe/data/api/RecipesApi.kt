package com.example.recipe.data.api

import com.example.recipe.models.data.RecipeR

/**
 * Апи для получения данных о рецептах
 */
interface RecipesApi {
    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [List<Recipe>] список рецептов или пустой список, если ошибка
     */
    fun get(query: String): List<RecipeR>?
}