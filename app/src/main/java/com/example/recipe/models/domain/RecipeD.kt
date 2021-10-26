package com.example.recipe.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Модель для отображения данных о рецептах для domain слоя
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param ingredientLines список ингредиентов
 */
@Parcelize
data class RecipeD(val uri: String,
                   val label: String,
                   val image: String,
                   val source: String,
                   val url: String,
                   val ingredientLines: List<String>) : Parcelable