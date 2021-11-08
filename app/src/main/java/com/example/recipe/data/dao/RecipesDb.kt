package com.example.recipe.data.dao

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

    /**
     * Добавить рецепт в избранное
     *
     * @param [recipe] рецепт
     */
    fun addToFavourites(recipe: RecipeDB)

    /**
     * Проверить, является ли рецепт избранным
     *
     * @param [recipe] рецепт
     * @return [Boolean] true - является избранным рецептом, false - не является избранным рецептом
     */
    fun isFavourite(recipe: RecipeDB): Boolean

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipeDB)
}