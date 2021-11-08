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
 * @param ingredientLines список ингредиентов
 * @param dietLabels вариант диетического питания
 * @param healthLabels отсутствующие аллергены
 * @param cuisineType вид кухни мира
 * @param mealType для какого приема пищи подходит блюдо
 * @param dishType вид блюда
 */
@Parcelize
data class RecipePresentationModel(val uri: String,
                                   val label: String,
                                   val image: String,
                                   val source: String,
                                   val url: String,
                                   val ingredientLines: List<String>,
                                   val dietLabels: List<String>,
                                   val healthLabels: List<String>,
                                   val cuisineType: List<String>,
                                   val mealType: List<String>,
                                   val dishType: List<String>,
                                   var isFavourite: Boolean = false) : Parcelable