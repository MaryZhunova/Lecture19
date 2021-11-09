package com.example.recipe.data.repository

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.data.api.Recipe
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject
import javax.inject.Named

/**
 * Реализация [RecipesRepository]
 *
 * @param recipesDao интерфейс для получения данных об избранных рецептах из базы данных
 * @param recipesApi апи для работы с сервераными данными
 * @param converter конвертер из Recipe в RecipeDomainModel
 * @param converterFromEntity из RecipeEntity в RecipeDomainModel
 * @param converterToEntity из RecipeDomainModel в RecipeEntity
 */
class RecipesRepositoryImpl @Inject constructor(@Named("dao") private val recipesDao: RecipesDao,
                                                private val recipesApi: RecipesApi,
                                                private val converter: Converter<Recipe, RecipeDomainModel>,
                                                private val converterFromEntity: Converter<RecipeEntity, RecipeDomainModel>,
                                                private val converterToEntity: Converter<RecipeDomainModel, RecipeEntity>): RecipesRepository {

    override fun get(query: String): List<RecipeDomainModel> {
        // Получить список Recipe из базы данных
        val recipes = recipesApi.get(query)
        for (recipe in recipes) {
            // Проверить, занесен ли рецепт в базу данных
            if (recipesDao.isFavourite(recipe.uri).isNotEmpty()) {
                // Заменить значение поля isFavourite на true
                recipe.isFavourite = true
            }
        }
        // Конвертировать список Recipe в список RecipeDomainModel
        return recipes.map(converter::convert)
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
}