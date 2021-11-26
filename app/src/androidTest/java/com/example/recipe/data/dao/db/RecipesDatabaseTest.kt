package com.example.recipe.data.dao.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.dao.entity.relations.RecipeWithIngredients
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipesDatabaseTest : TestCase() {
    private lateinit var db: RecipesDatabase
    private lateinit var recipesDao: RecipesDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RecipesDatabase::class.java).build()
        recipesDao = db.recipesDao
    }
    @Test
    fun testAddToFavourites() {
        recipesDao.addToFavourites(recipeEntity)

        val expectedResult = recipesDao.isFavourite("uri")
        assertThat(expectedResult.contains(recipeEntity)).isTrue()
    }

    @Test
    fun testIsFavourite() {
        val expectedResult = recipesDao.isFavourite("uri")

        assertThat(expectedResult.contains(recipeEntity)).isFalse()
    }

    @Test
    fun testDeleteFromFavourites() {
        recipesDao.addToFavourites(recipeEntity)
        recipesDao.deleteFromFavourites(recipeEntity)

        val expectedResult = recipesDao.isFavourite("uri")
        assertThat(expectedResult.contains(recipeEntity)).isFalse()
    }

    @Test
    fun testGetAllFavourites() {
        val expectedResult: List<RecipeWithIngredients> = listOf(
            RecipeWithIngredients(recipeEntity, listOf(ingredientEntity))
        )

        recipesDao.addIngredientLinesToFavourites(ingredientEntity)
        recipesDao.addToFavourites(recipeEntity)

        val result = recipesDao.getAllFavourites()

        assertThat(result == expectedResult).isTrue()
    }

    @Test
    fun testDeleteIngredient() {
        recipesDao.addToFavourites(recipeEntity)
        recipesDao.addIngredientLinesToFavourites(ingredientEntity)

        recipesDao.deleteIngredient(ingredientEntity)

        val result = recipesDao.getAllFavourites()

        assertThat(result[0].ingredients.isEmpty()).isTrue()
    }

    @Test
    fun testAddToMyRecipes() {
        recipesDao.addToMyRecipes(myRecipeEntity)

        val expectedResult = recipesDao.getAllMyRecipes()
        assertThat(expectedResult.contains(myRecipeEntity)).isTrue()
    }

    @Test
    fun testDeleteFromMyRecipes() {
        recipesDao.addToMyRecipes(myRecipeEntity)
        recipesDao.deleteFromMyRecipes(myRecipeEntity)

        val expectedResult = recipesDao.getAllMyRecipes()
        assertThat(expectedResult.contains(myRecipeEntity)).isFalse()

    }
    @Test
    fun testGetAllMyRecipes() {
        val recipes = listOf(myRecipeEntity)

        for (recipe in recipes) {
            recipesDao.addToMyRecipes(recipe)
        }

        val expectedResult = recipesDao.getAllMyRecipes()
        assertThat(expectedResult == recipes).isTrue()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    companion object {
        val recipeEntity = RecipeEntity("uri", "label", "image", "source",
            "url", true)
        val ingredientEntity = IngredientEntity("ingredient", "uri")
        val myRecipeEntity = MyRecipeEntity("name", "ingredients", "instructions", "image")

    }
}