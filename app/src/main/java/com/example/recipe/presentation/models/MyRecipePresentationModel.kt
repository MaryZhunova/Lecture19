package com.example.recipe.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Модель для отображения данных о рецептах для domain слоя
 *
 * @param recipeName название рецепта
 * @param ingredients список ингредиентов
 * @param instructions рецепт
 * @param image изображение блюда
 */

@Parcelize
data class MyRecipePresentationModel(
    val recipeName: String,
    val ingredients: String,
    val instructions: String,
    val image: String
) : Parcelable