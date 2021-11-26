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
import com.example.recipe.utils.converters.DomainToRecipeEntityConverter
import com.example.recipe.utils.converters.MyDomainToMyRecipeEntityConverter
import com.example.recipe.utils.converters.MyRecipeEntityToMyDomainConverter
import com.example.recipe.utils.converters.RecipeEntityToDomainConverter
import com.example.recipe.utils.converters.RecipeToDomainConverter
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException


class RecipesRepositoryImplTest {
    private lateinit var recipesRepositoryImpl: RecipesRepositoryImpl
    private val retrofitService: RetrofitService = mockk()
    private val recipesDao: RecipesDao = mockk()
    private val converter: Converter<Recipe, RecipeDomainModel>  = RecipeToDomainConverter()
    private val converterFromEntity: Converter<RecipeEntity, RecipeDomainModel>  = RecipeEntityToDomainConverter()
    private val converterFromMyEntity: Converter<MyRecipeEntity, MyRecipeDomainModel> = MyRecipeEntityToMyDomainConverter()
    private val converterToEntity: Converter<RecipeDomainModel, RecipeEntity> = DomainToRecipeEntityConverter()
    private val converterToMyEntity: Converter<MyRecipeDomainModel, MyRecipeEntity> = MyDomainToMyRecipeEntityConverter()

    @Before
    fun setUp() {
        recipesRepositoryImpl = RecipesRepositoryImpl(recipesDao, retrofitService, converter,
            converterFromEntity, converterFromMyEntity, converterToEntity, converterToMyEntity)
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
        every { retrofitService.get(queryArgumentForException, cont) } throws Exception()

        var testResult = false
        try {
            recipesRepositoryImpl.get(queryArgumentForException, cont)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetFavouriteRecipes() {
        val recipesDaoResponse: List<RecipeWithIngredients> = listOf(RecipeWithIngredients(RecipeEntity("uri", "label", "image", "source",
            "url", true), listOf(IngredientEntity("ingredient", "uri"))))

        val expectedResult: List<RecipeDomainModel> = listOf(
            RecipeDomainModel("uri", "label", "image", "source",
                "url", listOf("ingredient"), true))

        every { recipesDao.getAllFavourites() } returns recipesDaoResponse
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
        // TODO: 26.11.2021
//        val recipe = RecipeDomainModel("uri", "label", "image", "source",
//            "url", listOf("ingredient"), true)
//
//        recipesRepositoryImpl.addToFavourites(recipe)
//
//        val expectedResult = recipesDao.isFavourite("uri")
//        Truth.assertThat(expectedResult).isEqualTo(true)
    }
    @Test
    fun testDeleteFromFavourites() {
        // TODO: 26.11.2021
    }
    @Test
    fun testAddToMyRecipes() {
        // TODO: 26.11.2021
    }
    @Test
    fun testDeleteFromMyRecipes() {
        // TODO: 26.11.2021
    }

    @Test
    fun testGetMyRecipes() {
        val recipesDaoResponse = listOf(MyRecipeEntity("name", "ingredients", "instructions", "image"))
        every { recipesDao.getAllMyRecipes() } returns recipesDaoResponse

        val expectedResult = listOf(MyRecipeDomainModel("name", "ingredients", "instructions", "image"))

        every { recipesDao.getAllMyRecipes() } returns recipesDaoResponse

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
        const val queryArgument = "chicken"
        const val cont = "cont"
        const val queryArgumentForException = "chicn"
    }
}