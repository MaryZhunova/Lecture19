package com.example.recipe.domain

import com.example.recipe.models.domain.MyRecipeDomainModel
import com.example.recipe.models.domain.RecipeDomainModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Интерактор
 *
 * @param repository репозиторий
 */
class RecipesInteractor @Inject constructor(private val repository: RecipesRepository) {
    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @return [Observable<Pair<String, List<RecipeDomainModel>>>] String - строка с параметром для перехода на следующую страницу запроса, List<RecipeDomainModel> - список рецептов
     */
    fun get(query: String): Observable<Pair<String, List<RecipeDomainModel>>> {
        return repository.get(query)
    }

    /**
     * Получить список рецептов
     *
     * @param query ключевое слово для запроса
     * @param cont параметр для перехода на следующую страницу запроса
     * @return [Observable<Pair<String, List<RecipeDomainModel>>>] String - строка с параметром для перехода на следующую страницу запроса, List<RecipeDomainModel> - список рецептов
     */
    fun get(query: String, cont: String): Observable<Pair<String, List<RecipeDomainModel>>> {
        return repository.get(query, cont)
    }

    /**
     * Получить список избранных рецептов
     *
     * @return [List<RecipeDomainModel>] список рецептов
     */
    fun getFavouriteRecipes(): List<RecipeDomainModel> {
        return repository.getFavouriteRecipes()
    }

    /**
     * Добавить рецепт в избранное
     *
     * @param [recipe] рецепт
     */
    fun addToFavourites(recipe: RecipeDomainModel) {
        repository.addToFavourites(recipe)
    }

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipeDomainModel) {
        repository.deleteFromFavourites(recipe)
    }

    /**
     * Добавить рецепт в базу данных
     *
     * @param [recipe] рецепт
     */
    fun addToMyRecipes(recipe: MyRecipeDomainModel) {
        repository.addToMyRecipes(recipe)
    }

    /**
     * Удалить рецепт из базы данных
     *
     * @param [recipe] рецепт
     */
    fun deleteFromMyRecipes(recipe: MyRecipeDomainModel) {
        repository.deleteFromMyRecipes(recipe)
    }

    /**
     * Получить список рецептов из базы данных
     *
     * @return [List<MyRecipeDomainModel>] список рецептов
     */
    fun getMyRecipes(): List<MyRecipeDomainModel> {
        return repository.getMyRecipes()
    }
}