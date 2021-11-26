package com.example.recipe.data.repository

import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.dao.entity.relations.RecipeWithIngredients
import com.example.recipe.data.network.RetrofitService
import com.example.recipe.data.network.models.Hit
import com.example.recipe.data.network.models.Link
import com.example.recipe.data.network.models.Next
import com.example.recipe.data.network.models.Recipe
import com.example.recipe.data.network.models.RecipeResponse
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.utils.converters.Converter
import com.google.common.truth.Truth
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

/**
 * Тест проверки работы репозитория [RecipesRepositoryImpl]
 */
class RecipesRepositoryImplTest {
    private lateinit var recipesRepositoryImpl: RecipesRepositoryImpl
    private val retrofitService: RetrofitService = mockk()
    private val recipesDao: RecipesDao = mockk()
    private val converter: Converter<Recipe, RecipeDomainModel> = mockk()
    private val converterFromEntity: Converter<RecipeEntity, RecipeDomainModel> = mockk()
    private val converterFromMyEntity: Converter<MyRecipeEntity, MyRecipeDomainModel> = mockk()
    private val converterToEntity: Converter<RecipeDomainModel, RecipeEntity> = mockk()
    private val converterToMyEntity: Converter<MyRecipeDomainModel, MyRecipeEntity> = mockk()

    @Before
    fun setUp() {
        recipesRepositoryImpl = RecipesRepositoryImpl(
            recipesDao, retrofitService, converter,
            converterFromEntity, converterFromMyEntity, converterToEntity, converterToMyEntity
        )
    }

    @Test
    fun testGet() {
        val recipe = Recipe("uri", "label", "image", "source",
            "url", listOf("ingredientLines"))
        val retrofitResponse: Observable<RecipeResponse> = Observable.just(RecipeResponse(
            Link(Next(nextPage)), listOf(Hit(recipe))))

        every { retrofitService.get(queryArgument) } returns retrofitResponse

        recipesRepositoryImpl.get(queryArgument).subscribe { result ->
            Truth.assertThat(result.first).isEqualTo(nextPage)
            Truth.assertThat(result.second).isEqualTo(listOf(recipeDomainModel))
        }

        val testResult = recipesRepositoryImpl.get(queryArgument)
        Truth.assertThat(testResult).isInstanceOf(Observable::class.java)
    }

    @Test
    fun testGetException() {
        every { retrofitService.get(queryArgumentForException) } throws Exception()

        var testResult = false
        try {
            recipesRepositoryImpl.get(queryArgumentForException)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetTwoArguments() {
        val recipe = Recipe("uri", "label", "image", "source",
            "url", listOf("ingredientLines"))
        val retrofitResponse: Observable<RecipeResponse> = Observable.just(RecipeResponse(
            Link(Next(nextPage)), listOf(Hit(recipe))))

        every { retrofitService.get(queryArgument, nextPage) } returns retrofitResponse

        recipesRepositoryImpl.get(queryArgument, nextPage).subscribe { result ->
            Truth.assertThat(result.first).isEqualTo(nextPage)
            Truth.assertThat(result.second).isEqualTo(listOf(recipeDomainModel))
        }

        val testResult = recipesRepositoryImpl.get(queryArgument, nextPage)
        Truth.assertThat(testResult).isInstanceOf(Observable::class.java)
    }

    @Test
    fun testGetTwoArgumentsException() {
        every { retrofitService.get(queryArgumentForException, nextPage) } throws Exception()

        var testResult = false
        try {
            recipesRepositoryImpl.get(queryArgumentForException, nextPage)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetFavouriteRecipes() {
        val recipesDaoResponse: List<RecipeWithIngredients> = listOf(
            RecipeWithIngredients(
                recipeEntity, listOf(ingredientEntity)
            )
        )
        val expectedResult: List<RecipeDomainModel> = listOf(
            recipeDomainModel
        )

        every { recipesDao.getAllFavourites() } returns recipesDaoResponse
        every { converterFromEntity.convert(recipeEntity) } returns recipeDomainModel

        val testResult = recipesRepositoryImpl.getFavouriteRecipes()
        Truth.assertThat(testResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetFavouriteRecipesException() {
        every { recipesDao.getAllFavourites() } throws Exception()

        var testResult = false
        try {
            recipesRepositoryImpl.getFavouriteRecipes()
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testAddToFavourites() {
        every { recipesDao.addToFavourites(recipeEntity) } returns Unit
        every { recipesDao.addIngredientLinesToFavourites(ingredientEntity) } returns Unit
        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        recipesRepositoryImpl.addToFavourites(recipeDomainModel)

        verify {
            recipesDao.addToFavourites(recipeEntity)
            recipesDao.addIngredientLinesToFavourites(ingredientEntity)
        }
        confirmVerified(recipesDao)
    }

    @Test
    fun testAddToFavouritesException() {
        every { recipesDao.addToFavourites(recipeEntity) } throws java.lang.Exception()
        every { recipesDao.addIngredientLinesToFavourites(ingredientEntity) } returns Unit
        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        var testResult = false
        try {
            recipesRepositoryImpl.addToFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testAddToFavouritesIngredientException() {
        every { recipesDao.addToFavourites(recipeEntity) } returns Unit
        every { recipesDao.addIngredientLinesToFavourites(ingredientEntity) } throws java.lang.Exception()
        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        var testResult = false
        try {
            recipesRepositoryImpl.addToFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }


    @Test
    fun testDeleteFromFavourites() {
        every { recipesDao.deleteFromFavourites(recipeEntity) } returns Unit
        every { recipesDao.deleteIngredient(ingredientEntity) } returns Unit

        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        recipesRepositoryImpl.deleteFromFavourites(recipeDomainModel)

        verify {
            recipesDao.deleteFromFavourites(recipeEntity)
            recipesDao.deleteIngredient(ingredientEntity)
        }
        confirmVerified(recipesDao)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        every { recipesDao.deleteFromFavourites(recipeEntity) } throws java.lang.Exception()
        every { recipesDao.deleteIngredient(ingredientEntity) } returns Unit
        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        var testResult = false
        try {
            recipesRepositoryImpl.deleteFromFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testDeleteFromFavouritesIngredientException() {
        every { recipesDao.deleteFromFavourites(recipeEntity) } returns Unit
        every { recipesDao.deleteIngredient(ingredientEntity) } throws java.lang.Exception()
        every { converterToEntity.convert(recipeDomainModel) } returns recipeEntity

        var testResult = false
        try {
            recipesRepositoryImpl.deleteFromFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testAddToMyRecipes() {
        every { recipesDao.addToMyRecipes(myRecipeEntityModel) } returns Unit
        every { converterToMyEntity.convert(myRecipeDomainModel) } returns myRecipeEntityModel

        recipesRepositoryImpl.addToMyRecipes(myRecipeDomainModel)

        verify {
            recipesDao.addToMyRecipes(myRecipeEntityModel)
        }
        confirmVerified(recipesDao)
    }

    @Test
    fun testAddToMyRecipesException() {
        every { recipesDao.addToMyRecipes(myRecipeEntityModel) } throws java.lang.Exception()
        every { converterToMyEntity.convert(myRecipeDomainModel) } returns myRecipeEntityModel

        var testResult = false
        try {
            recipesRepositoryImpl.addToMyRecipes(myRecipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testDeleteFromMyRecipes() {
        every { recipesDao.deleteFromMyRecipes(myRecipeEntityModel) } returns Unit
        every { converterToMyEntity.convert(myRecipeDomainModel) } returns myRecipeEntityModel

        recipesRepositoryImpl.deleteFromMyRecipes(myRecipeDomainModel)

        verify {
            recipesDao.deleteFromMyRecipes(myRecipeEntityModel)
        }
        confirmVerified(recipesDao)
    }

    @Test
    fun testDeleteFromMyRecipesException() {
        every { recipesDao.deleteFromMyRecipes(myRecipeEntityModel) } throws java.lang.Exception()
        every { converterToMyEntity.convert(myRecipeDomainModel) } returns myRecipeEntityModel

        var testResult = false
        try {
            recipesRepositoryImpl.deleteFromMyRecipes(myRecipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetMyRecipes() {
        val recipesDaoResponse = listOf(myRecipeEntityModel)
        val expectedResult = listOf(myRecipeDomainModel)

        every { recipesDao.getAllMyRecipes() } returns recipesDaoResponse
        every { recipesDao.getAllMyRecipes() } returns recipesDaoResponse
        every { converterFromMyEntity.convert(myRecipeEntityModel) } returns myRecipeDomainModel

        val testResult = recipesRepositoryImpl.getMyRecipes()
        Truth.assertThat(testResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetMyRecipesException() {
        every { recipesDao.getAllMyRecipes() } throws Exception()

        var testResult = false
        try {
            recipesRepositoryImpl.getMyRecipes()
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    private companion object {
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
        val recipeEntity = RecipeEntity(
            "uri", "label", "image", "source",
            "url", true
        )
        val ingredientEntity = IngredientEntity("ingredient", "uri")
        val myRecipeDomainModel = MyRecipeDomainModel("name", "ingredients", "instructions", "image")
        val myRecipeEntityModel = MyRecipeEntity("name", "ingredients", "instructions", "image")
        const val queryArgument = "chicken"
        const val nextPage = "nextPage"
        const val queryArgumentForException = "chicn"
    }
}