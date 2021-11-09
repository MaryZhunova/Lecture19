package com.example.recipe.models.data.api

/**
 * Модель для отображения данных о рецептах  для работы с сервером
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
 * @param isFavourite добавлено ли блюдо в избранное
 */
data class Recipe(val uri: String,
                  val label: String,
                  val image: String,
                  val source: String,
                  val url: String,
                  val ingredientLines: List<String>,
//                  val dietLabels: List<String>,
//                  val healthLabels: List<String>,
//                  val cuisineType: List<String>,
//                  val mealType: List<String>,
//                  val dishType: List<String>,
                  var isFavourite: Boolean = false)