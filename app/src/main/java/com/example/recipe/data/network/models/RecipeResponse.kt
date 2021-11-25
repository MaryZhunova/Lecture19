package com.example.recipe.data.network.models

import com.google.gson.annotations.SerializedName

/**
 * Модель для отображения данных о рецептах  для работы с сервером
 *
 * @param link ссылка на следующую страницу
 * @param hits Список "хитов", каждый из которых содержит информацию о рецепте
 */
data class RecipeResponse(@SerializedName("_links") val link: Link, val hits: List<Hit>)

/**
 * Модель для отображения ссылки на следующую страницу
 *
 * @param next ссылка на следующую страницу
 */
data class Link(val next: Next?)

/**
 * Модель для отображения ссылки на следующую страницу
 *
 * @param href ссылка на следующую страницу
 */
data class Next(val href: String)


