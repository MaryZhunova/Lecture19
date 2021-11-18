package com.example.recipe.models.domain

/**
 * Модель для отображения данных о рецептах для domain слоя
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param ingredientLines список ингредиентов
 * @param isFavourite добавлено ли блюдо в избранное
 */

data class RecipeDomainModel(
    val uri: String,
    val label: String,
    val image: String,
    val source: String,
    val url: String,
    var ingredientLines: List<String>,
    var isFavourite: Boolean = false)