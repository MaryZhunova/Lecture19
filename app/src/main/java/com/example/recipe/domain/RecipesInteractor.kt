package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Интерактор
 *
 * @param repository репозиторий с рецептами
 */
class RecipesInteractor @Inject constructor(private val repository: BaseRepository) {
    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [List<Recipe>] список рецептов или пустой список, если ошибка
     */
    fun get(query: String): List<RecipeDomainModel>? {
        return repository.get(query)
    }

}