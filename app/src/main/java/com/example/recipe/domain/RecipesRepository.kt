package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel
import io.reactivex.Observable

/**
 * Репозиторий для работы с данными
 */
interface RecipesRepository {

    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun get(query: String): Observable<List<RecipeDomainModel>>

    /**
     * Получить список избранных рецептов
     *
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun getFavouriteRecipes(): List<RecipeDomainModel>

    /**
     * Добавить рецепт в избранное
     *
     * @param [recipe] рецепт
     */
    fun addToFavourites(recipe: RecipeDomainModel)

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipeDomainModel)
}