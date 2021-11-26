//package com.example.recipe
//
//import com.example.recipe.data.api.RecipesApiImpl
//import com.example.recipe.data.repository.RecipesRepositoryImpl
//import com.example.recipe.utils.converters.Converter
//import com.example.recipe.models.data.api.RecipeModel
//import com.example.recipe.domain.models.RecipeDomainModel
//import com.google.common.truth.Truth
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.Test
//import org.junit.Before
//import java.lang.Exception
//
//class OkHttpRepositoryTest {
//    private val api: RecipesApiImpl = mockk()
//    private val db: RecipesDaoImpl = mockk()
//    private lateinit var converter: Converter<RecipeModel, RecipeDomainModel>
//    private lateinit var repositoryImpl: RecipesRepositoryImpl
//
//    @Before
//    fun setUp() {
//        repositoryImpl = RecipesRepositoryImpl(db, api)
//    }
//
//    @Test
//    fun getTest() {
//        val apiResult: List<RecipeModel> = listOf(
//            RecipeModel("uri", "label", "image", "source",
//            "url", listOf("ingredientLines"), listOf("dietLabels"), listOf("healthLabels"), listOf("cuisineType"), listOf("mealType"), listOf("dishType"))
//        )
//        val expectedResult: List<RecipeDomainModel> = listOf(
//            RecipeDomainModel("uri", "label", "image", "source",
//            "url", listOf("ingredientLines"), listOf("dietLabels"), listOf("healthLabels"), listOf("cuisineType"), listOf("mealType"), listOf("dishType"))
//        )
//        every { api.get(queryArgument) } returns apiResult
//
//        val testResult = repositoryImpl.get(queryArgument)
//
//        Truth.assertThat(testResult).isEqualTo(expectedResult)
//    }
//
//    @Test
//    fun testGetException() {
//
//        every { api.get(queryArgumentForException) } throws Exception()
//
//        var testResult = false
//        try {
//            repositoryImpl.get(queryArgumentForException)
//        } catch (e: Exception) {
//            testResult = true
//        }
//
//        Truth.assertThat(testResult).isTrue()
//    }
//
//    private companion object {
//        const val queryArgument = "Nyama"
//        const val queryArgumentForException = "nyam"
//    }
//}