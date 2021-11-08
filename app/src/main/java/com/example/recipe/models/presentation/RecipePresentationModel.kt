package com.example.recipe.models.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Модель для отображения данных о рецептах для presentation слоя
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param isFavourite добавлено ли блюдо в избранное
 */
@Parcelize
data class RecipePresentationModel(val uri: String,
                                   val label: String,
                                   val image: String,
                                   val source: String,
                                   val url: String,
                                   var isFavourite: Boolean = false) : Parcelable