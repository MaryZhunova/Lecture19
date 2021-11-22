package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel
import io.reactivex.Observable
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
    fun get(query: String): Observable<Pair<String, List<RecipeDomainModel>>> {
        return repository.get(query)
    }

    fun get(query: String, cont: String): Observable<Pair<String, List<RecipeDomainModel>>> {
        return repository.get(query, cont)
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