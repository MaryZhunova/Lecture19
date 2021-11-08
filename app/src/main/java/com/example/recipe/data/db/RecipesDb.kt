package com.example.recipe.data.db

import com.example.recipe.models.data.RecipeDB

/**
 * Интерфейс для получения данных об избранных рецептах из базы данных
 */
interface RecipesDb {
    /**
     * Получить список избранных рецептов
     *
     * @return [List<RecipeDB>] список рецептов
     */
    fun getFavouriteRecipes(): List<RecipeDB>
}