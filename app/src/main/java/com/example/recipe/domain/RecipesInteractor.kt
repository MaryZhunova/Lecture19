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
}