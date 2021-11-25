package com.example.recipe.domain.models

/**
 * Модель для отображения данных о рецептах для presentation слоя
 *
 * @param recipeName название рецепта
 * @param ingredients список ингредиентов
 * @param instructions рецепт
 * @param image изображение блюда
 */

data class MyRecipeDomainModel(
    val recipeName: String,
    val ingredients: String,
    val instructions: String,
    val image: String
)