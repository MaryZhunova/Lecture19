package com.example.recipe.data.repository

import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.network.RetrofitService
import com.example.recipe.models.data.api.Recipe
import com.example.recipe.models.data.api.RecipeResponse
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.domain.RecipeDomainModel2
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

/**
 * Реализация [RecipesRepository]
 *
 * @param recipesDao интерфейс для получения данных об избранных рецептах из базы данных
 * @param retrofitService сервис для работы с сервером
 * @param converter конвертер из RecipeModel в RecipeDomainModel
 * @param converterFromEntity из RecipeEntity в RecipeDomainModel
 * @param converterToEntity из RecipeDomainModel в RecipeEntity
 */
class RecipesRepositoryImpl @Inject constructor(
    @Named("dao") private val recipesDao: RecipesDao,
    private val retrofitService: RetrofitService,
    private val converter: Converter<RecipeResponse, RecipeDomainModel2>,
    private val converterFromEntity: Converter<RecipeEntity, RecipeDomainModel>,
    private val converterToEntity: Converter<RecipeDomainModel, RecipeEntity>
) : RecipesRepository {

    override fun get (query: String): Observable<RecipeDomainModel2> {
        return retrofitService.get(query)
//            .map { it.hits.map { hit -> hit.recipe } }
            .map { recipe -> converter.convert(recipe) }
//            .map { recipes -> recipes.map(::checkFavourites) }
    }

    override fun get(query: String, cont: String): Observable<RecipeDomainModel2> {
        return retrofitService.get(query, cont)
            .map { recipe -> converter.convert(recipe) }
    }

    private fun checkFavourites(recipe: RecipeDomainModel): RecipeDomainModel {
        // Проверить, есть ли рецепт в избранном
        if (recipesDao.isFavourite(recipe.uri).isNotEmpty()) {
            // Заменить значение поля isFavourite на true
            recipe.isFavourite = true
        }
        return recipe
    }

    override fun getFavouriteRecipes(): List<RecipeDomainModel> {
        // Создать изменяемый список для занесения рецептов
        val result: MutableList<RecipeDomainModel> = mutableListOf()
        // Получить список RecipeWithIngredients из базы данных
        val recipesEntities = recipesDao.getAllFavourites()

        for (recipe in recipesEntities) {
            // Конвертировать элемент списка в RecipeDomainModel
            val recipeDomainModel = converterFromEntity.convert(recipe.recipeEntity)
            recipeDomainModel.ingredientLines =
                recipe.ingredients.map { ingredientEntity -> ingredientEntity.ingredient }
            result.add(recipeDomainModel)
        }
        return result.toList()
    }

    override fun addToFavourites(recipe: RecipeDomainModel) {
        // Конверитировать RecipeDomainModel в RecipeEntity
        // Добавить RecipeEntity в базу данных
        recipesDao.addToFavourites(converterToEntity.convert(recipe))
        // Конверитировать String в IngredientEntity
        // Добавить IngredientEntity в базу данных
        for (ingredient in recipe.ingredientLines) {
            recipesDao.addIngredientLinesToFavourites(IngredientEntity(ingredient, recipe.uri))
        }
    }

    override fun deleteFromFavourites(recipe: RecipeDomainModel) {
        //Конверитировать RecipeDomainModel в RecipeEntity
        // Удалить RecipeEntity из базы данных
        recipesDao.deleteFromFavourites(converterToEntity.convert(recipe))
        // Конверитировать String в IngredientEntity
        // Удалить IngredientEntity из базы данных
        for (ingredient in recipe.ingredientLines) {
            recipesDao.deleteIngredient(IngredientEntity(ingredient, recipe.uri))
        }
    }

    companion object {
        var next: String? = null
    }
}