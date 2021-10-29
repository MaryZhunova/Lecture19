package com.example.recipe.domain

import com.example.recipe.models.domain.RecipeDomainModel

/**
 * Репозиторий для работы с данными
 */
interface BaseRepository {

    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [List<Recipe>] список рецептов или пустой список, если ошибка
     */
    fun get(query: String): List<RecipeDomainModel>
}