package com.example.recipe.presentation.recipedetail

/**
 * Интерфейс, описывающий возможности View
 */
interface RecipeDetailView {
    /**
     * Отобразить информацию об ошибке, если она возникла в ходе добавления/удаления рецепта в избранное
     *
     * @param throwable ошибка
     */
    fun showError(throwable: Throwable)
}