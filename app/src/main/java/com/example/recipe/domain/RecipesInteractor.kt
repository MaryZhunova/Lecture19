package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Интерактор
 *
 * @param repository репозиторий
 */
class RecipesInteractor @Inject constructor(private val repository: RecipesRepository) {
    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun get(query: String): List<RecipeDomainModel> {
        return repository.get(query)
    }

    /**
     * Получить список избранных рецептов
     *
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun getFavouriteRecipes(): List<RecipeDomainModel> {
        return repository.getFavouriteRecipes()
    }

    /**
     * Добавить рецепт в избранное
     *
     * @param [recipe] рецепт
     */
    fun addToFavourites(recipe: RecipeDomainModel) {
        repository.addToFavourites(recipe)
    }

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipeDomainModel) {
        repository.deleteFromFavourites(recipe)
    }

}