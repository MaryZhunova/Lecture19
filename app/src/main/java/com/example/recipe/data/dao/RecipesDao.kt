package com.example.recipe.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.RecipesDbContract.RecipesEntry
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.dao.entity.relations.RecipeWithIngredients

/**
 * Интерфейс для получения данных об избранных рецептах из базы данных
 */
@Dao
interface RecipesDao {
    /**
     * Добавить рецепт в базу данных
     *
     * @param [recipe] рецепт
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavourites(recipe: RecipeEntity)

    /**
     * Добавить ингредиент в базу данных
     *
     * @param [ingredient] ингредиент
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIngredientLinesToFavourites(ingredient: IngredientEntity)

    /**
     * Проверить, занесен ли рецепт в базу данных
     *
     * @param [uri] uri рецепта
     * @return List<RecipeEntity> список с рецептом, если он есть в базе данных, пустой список - если нет
     */
    @Query("SELECT * FROM " + RecipesEntry.TABLE_NAME_RECIPES + " WHERE " + RecipesEntry.URI + " = :uri")
    fun isFavourite(uri: String): List<RecipeEntity>

    /**
     * Удалить рецепт из базы данных
     *
     * @param [recipe] рецепт
     */
    @Delete
    fun deleteFromFavourites(recipe: RecipeEntity)

    /**
     * Удалить ингредиент из базы данных
     *
     * @param [ingredient] ингредиент
     */
    @Delete
    fun deleteIngredient(ingredient: IngredientEntity)

    /**
     * Получить все рецепты и ингредиенты из базы данных
     *
     * @return List<RecipeWithIngredients> список с рецептами и ингридиентами
     */
    @Transaction
    @Query("SELECT * FROM " + RecipesEntry.TABLE_NAME_RECIPES + " ORDER BY " + RecipesEntry.LABEL)
    fun getAllFavourites(): List<RecipeWithIngredients>
}