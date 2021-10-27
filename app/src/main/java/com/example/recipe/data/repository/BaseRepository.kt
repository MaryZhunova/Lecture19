package com.example.recipe.data.repository

import com.example.recipe.data.model.Recipe

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
    fun get(query: String): List<Recipe>
}