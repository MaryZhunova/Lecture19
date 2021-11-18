package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel

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
    fun get(query: String): List<RecipeDomainModel>
}