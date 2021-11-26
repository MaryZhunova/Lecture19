package com.example.recipe.data.repository

import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.dao.entity.relations.RecipeWithIngredients
import com.example.recipe.data.network.RetrofitService
import com.example.recipe.data.network.models.Recipe
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.utils.converters.Converter
import com.google.common.truth.Truth
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class RecipesRepositoryImplTest {
    private lateinit var recipesRepositoryImpl: RecipesRepositoryImpl
    private val retrofitService: RetrofitService = mockk()
    private val recipesDao: RecipesDao = mockk()
    private val converter: Converter<Recipe, RecipeDomainModel> = mockk()

    //        RecipeToDomainConverter()
    private val converterFromEntity: Converter<RecipeEntity, RecipeDomainModel> = mockk()

    //        RecipeEntityToDomainConverter()
    private val converterFromMyEntity: Converter<MyRecipeEntity, MyRecipeDomainModel> = mockk()

    //        MyRecipeEntityToMyDomainConverter()
    private val converterToEntity: Converter<RecipeDomainModel, RecipeEntity> = mockk()

    //        DomainToRecipeEntityConverter()
    private val converterToMyEntity: Converter<MyRecipeDomainModel, MyRecipeEntity> = mockk()
//        MyDomainToMyRecipeEntityConverter()

    @Before
    fun setUp() {
        recipesRepositoryImpl = RecipesRepositoryImpl(
            recipesDao, retrofitService, converter,
            converterFromEntity, converterFromMyEntity, converterToEntity, converterToMyEntity
        )
    }

    @Test
    fun testGet() {
        // TODO: 26.11.2021
//        val retrofitResponse: Observable<RecipeResponse> = Observable.just(RecipeResponse(Link(Next("...")), listOf(Hit(Recipe("uri", "label", "image", "source",
//            "url", listOf("ingredientLines"))))))
//
//        val expectedResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair("...", listOf(
//            RecipeDomainModel("uri", "label", "image", "source",
//                "url", listOf("ingredientLines"), false))))
//
//        every { retrofitService.get(queryArgument) } returns retrofitResponse
//
//        val testResult = recipesRepositoryImpl.get(queryArgument)
//
//        Truth.assertThat(testResult).isEqualTo(expectedResult)
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
        // TODO: 26.11.2021
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