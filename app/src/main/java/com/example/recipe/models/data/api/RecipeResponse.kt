package com.example.recipe.models.data.api

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param hits Список "хитов", каждый из которых содержит информацию о рецепте
 */
data class RecipeResponse(val hits: List<Hit>)


