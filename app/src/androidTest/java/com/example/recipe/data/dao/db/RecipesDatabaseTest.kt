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
        val recipe = RecipeEntity("uri", "label", "image", "source",
            "url", true)

        recipesDao.addToFavourites(recipe)

        val expectedResult = recipesDao.isFavourite("uri")
        assertThat(expectedResult.contains(recipe)).isTrue()
    }

    @Test
    fun testIsFavourite() {
        val recipe = RecipeEntity("uri", "label", "image", "source",
            "url", true)

        val expectedResult = recipesDao.isFavourite("uri")

        assertThat(expectedResult.contains(recipe)).isFalse()
    }

    @Test
    fun testDeleteFromFavourites() {
        val recipe = RecipeEntity("uri", "label", "image", "source",
            "url", true)

        recipesDao.addToFavourites(recipe)
        recipesDao.deleteFromFavourites(recipe)

        val expectedResult = recipesDao.isFavourite("uri")
        assertThat(expectedResult.contains(recipe)).isFalse()
    }

    @Test
    fun testGetAllFavourites() {
        val recipeEntity = RecipeEntity("uri", "label", "image", "source",
            "url", true)
        val ingredientEntity = IngredientEntity("ingredient", "uri")
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
        val recipeEntity = RecipeEntity("uri", "label", "image", "source",
            "url", true)
        val ingredientEntity = IngredientEntity("ingredient", "uri")

        recipesDao.addToFavourites(recipeEntity)
        recipesDao.addIngredientLinesToFavourites(ingredientEntity)

        recipesDao.deleteIngredient(ingredientEntity)

        val result = recipesDao.getAllFavourites()

        assertThat(result[0].ingredients.isEmpty()).isTrue()
    }

    @Test
    fun testAddToMyRecipes() {
        val recipe = MyRecipeEntity("name", "ingredients", "instructions", "image")

        recipesDao.addToMyRecipes(recipe)

        val expectedResult = recipesDao.getAllMyRecipes()
        assertThat(expectedResult.contains(recipe)).isTrue()
    }

    @Test
    fun testDeleteFromMyRecipes() {
        val recipe = MyRecipeEntity("name", "ingredients", "instructions", "image")

        recipesDao.addToMyRecipes(recipe)
        recipesDao.deleteFromMyRecipes(recipe)

        val expectedResult = recipesDao.getAllMyRecipes()
        assertThat(expectedResult.contains(recipe)).isFalse()

    }
    @Test
    fun testGetAllMyRecipes() {
        val recipes = listOf(MyRecipeEntity("name", "ingredients", "instructions", "image"))

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
}