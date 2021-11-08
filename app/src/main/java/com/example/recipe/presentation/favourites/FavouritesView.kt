package com.example.recipe.presentation.favourites

import com.example.recipe.models.presentation.RecipePresentationModel

/**
 * Интерфейс, описывающий возможности View
 */
interface FavouritesView {
    /**
     * Показать/скрыть ProgressBar
     *
     * @param isVisible true - показать, false - скрыть
     */
    fun showProgress(isVisible: Boolean)

    /**
     * Отобразить информацию об ошибке, если она возникла в ходе запроса данных
     *
     * @param throwable ошибка
     */
    fun showError(throwable: Throwable)

    /**
     * Отобразить данные о рецептах
     *
     * @param recipes список приложений.
     */
    fun showData(recipes: MutableList<RecipePresentationModel>)
}