package com.example.recipe.models.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param hits Список "хитов", каждый из которых содержит информацию о рецепте
 */
@Parcelize
data class RecipeResponse(val hits: List<Hit>) : Parcelable

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param recipe рецепт
 */
@Parcelize
data class Hit(val recipe: Recipe) : Parcelable

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param isFavourite добавлено ли блюдо в избранное
 */
@Parcelize
data class Recipe(val uri: String,
                  val label: String,
                  val image: String,
                  val source: String,
                  val url: String,
                  var isFavourite: Boolean = false) : Parcelable
