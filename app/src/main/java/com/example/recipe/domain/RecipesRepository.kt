package com.example.recipe.domain

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import io.reactivex.Observable

/**
 * Репозиторий для работы с данными
 */
interface RecipesRepository {

    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [Observable<Pair<String, List<RecipeDomainModel>>>] String - строка с параметром для перехода на следующую страницу запроса, List<RecipeDomainModel> - список рецептов
     */
    fun get(query: String): Observable<Pair<String, List<RecipeDomainModel>>>

    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @param cont параметр для перехода на следующую страницу запроса
     * @return [Observable<Pair<String, List<RecipeDomainModel>>>] String - строка с параметром для перехода на следующую страницу запроса, List<RecipeDomainModel> - список рецептов
     */
    fun get(query: String, cont: String): Observable<Pair<String, List<RecipeDomainModel>>>

    /**
     * Получить список избранных рецептов
     *
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun getFavouriteRecipes(): List<RecipeDomainModel>

    /**
     * Добавить рецепт в избранное
     *
     * @param [recipe] рецепт
     */
    fun addToFavourites(recipe: RecipeDomainModel)

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipeDomainModel)

    /**
     * Добавить рецепт в базу данных
     *
     * @param [recipe] рецепт
     */
    fun addToMyRecipes(recipe: MyRecipeDomainModel)

    /**
     * Удалить рецепт из базы данных
     *
     * @param [recipe] рецепт
     */
    fun deleteFromMyRecipes(recipe: MyRecipeDomainModel)

    /**
     * Получить список рецептов из базы данных
     *
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun getMyRecipes(): List<MyRecipeDomainModel>

}