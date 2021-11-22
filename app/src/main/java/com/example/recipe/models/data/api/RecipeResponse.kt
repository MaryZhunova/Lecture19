package com.example.recipe.models.data.api

import com.google.gson.annotations.SerializedName

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param hits Список "хитов", каждый из которых содержит информацию о рецепте
 */
data class RecipeResponse(@SerializedName("_links") val link: Link, val hits: List<Hit>)

data class Link(val next: Next)

data class Next(val href: String)


