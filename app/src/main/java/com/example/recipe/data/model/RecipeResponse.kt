package com.example.recipe.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Модель для отображения данных о рецептах
 *
 * @param hits Список "хитов", каждый из которых содержит информацию о рецепте
 */
@Parcelize
data class RecipeResponse(val hits: List<Hit>) : Parcelable

/**
 * Модель для отображения данных о рецептах
 *
 * @param recipe рецепт
 */
@Parcelize
data class Hit(val recipe: Recipe) : Parcelable

/**
 * Модель для отображения данных о рецептах
 *
 * @param uri uri данного рецепта
 * @param label название рецепта
 * @param image изображение блюда
 * @param source источник рецепта
 * @param url ссылка на рецепт на стороннем ресурсе
 * @param ingredientLines список ингредиентов
 */
@Parcelize
data class Recipe(val uri: String,
                  val label: String,
                  val image: String,
                  val source: String,
                  val url: String,
                  val ingredientLines: List<String>) : Parcelable
