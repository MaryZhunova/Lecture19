package com.example.recipe.data.network.models

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param ingredientLines список ингредиентов
 * @param isFavourite добавлено ли блюдо в избранное
 */
data class Recipe(
    val uri: String,
    val label: String,
    val image: String,
    val source: String,
    val url: String,
    val ingredientLines: List<String>,
    var isFavourite: Boolean = false
)