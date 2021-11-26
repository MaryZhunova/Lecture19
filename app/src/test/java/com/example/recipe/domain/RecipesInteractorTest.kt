package com.example.recipe.domain

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import com.google.common.truth.Truth
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

/**
 * Тест проверки работы интерактора [RecipesInteractor]
 */
class RecipesInteractorTest {
    private lateinit var recipesInteractor: RecipesInteractor
    private val repository: RecipesRepository = mockk()

    @Before
    fun setUp() {
        recipesInteractor = RecipesInteractor(repository)
    }

    @Test
    fun testGet() {
        every { repository.get(queryArgument) } returns observableExpectedResult

        val testResult = recipesInteractor.get(queryArgument)

        Truth.assertThat(testResult).isEqualTo(observableExpectedResult)
    }

    @Test
    fun testGetException() {
        every { repository.get(queryArgumentForException) } throws Exception()

        var testResult = false
        try {
            recipesInteractor.get(queryArgumentForException)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetTwoArguments() {
        every { repository.get(queryArgument, nextPage) } returns observableExpectedResult

        val testResult = recipesInteractor.get(queryArgument, nextPage)

        Truth.assertThat(testResult).isEqualTo(observableExpectedResult)
    }

    @Test
    fun testGetTwoArgumentsException() {
        every { repository.get(queryArgumentForException, nextPage) } throws Exception()

        var testResult = false
        try {
            recipesInteractor.get(queryArgumentForException, nextPage)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }
    @Test
    fun testGetFavouriteRecipes() {
        val repositoryResult: List<RecipeDomainModel> = listOf(recipeDomainModel)

        every { repository.getFavouriteRecipes() } returns repositoryResult
        val testResult = recipesInteractor.getFavouriteRecipes()
        Truth.assertThat(testResult).isEqualTo(repositoryResult)
    }

    @Test
    fun testGetFavouriteRecipesException() {
        every { repository.getFavouriteRecipes() } throws Exception()

        var testResult = false
        try {
            recipesInteractor.getFavouriteRecipes()
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testAddToFavourites() {
        every { repository.addToFavourites(recipeDomainModel) } returns Unit

        recipesInteractor.addToFavourites(recipeDomainModel)

        verify {
            repository.addToFavourites(recipeDomainModel)
        }
        confirmVerified(repository)
    }

    @Test
    fun testAddToFavouritesException() {
        every { repository.addToFavourites(recipeDomainModel) } throws Exception()
        var testResult = false
        try {
            recipesInteractor.addToFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testDeleteFromFavourites() {
        every { repository.deleteFromFavourites(recipeDomainModel) } returns Unit

        recipesInteractor.deleteFromFavourites(recipeDomainModel)

        verify {
            repository.deleteFromFavourites(recipeDomainModel)
        }
        confirmVerified(repository)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        every { repository.deleteFromFavourites(recipeDomainModel) } throws Exception()
        var testResult = false
        try {
            recipesInteractor.deleteFromFavourites(recipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testAddToMyRecipes() {
        every { repository.addToMyRecipes(myRecipeDomainModel) } returns Unit

        recipesInteractor.addToMyRecipes(myRecipeDomainModel)

        verify {
            repository.addToMyRecipes(myRecipeDomainModel)
        }
        confirmVerified(repository)
    }

    @Test
    fun testAddToMyRecipesException() {
        every { repository.addToMyRecipes(myRecipeDomainModel) } throws Exception()

        var testResult = false
        try {
            recipesInteractor.addToMyRecipes(myRecipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testDeleteFromMyRecipes() {
        every { repository.deleteFromMyRecipes(myRecipeDomainModel) } returns Unit

        recipesInteractor.deleteFromMyRecipes(myRecipeDomainModel)

        verify {
            repository.deleteFromMyRecipes(myRecipeDomainModel)
        }
        confirmVerified(repository)
    }

    @Test
    fun testDeleteFromMyRecipesException() {
        every { repository.deleteFromMyRecipes(myRecipeDomainModel) } throws Exception()

        var testResult = false
        try {
            recipesInteractor.deleteFromMyRecipes(myRecipeDomainModel)
        } catch (e: Exception) {
            testResult = true
        }
        Truth.assertThat(testResult).isTrue()
    }

    @Test
    fun testGetMyRecipes() {
        val repositoryResult: List<MyRecipeDomainModel> = listOf(myRecipeDomainModel)

        every { repository.getMyRecipes() } returns repositoryResult
        val testResult = recipesInteractor.getMyRecipes()
        Truth.assertThat(testResult).isEqualTo(repositoryResult)
    }

    @Test
    fun testGetMyRecipesException() {
        every { repository.getMyRecipes() } throws Exception()

        var testResult = false
        try {
            recipesInteractor.getMyRecipes()
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
        val myRecipeDomainModel = MyRecipeDomainModel("name", "ingredients", "instructions", "image")
        val observableExpectedResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair("...", listOf(recipeDomainModel)))
        const val queryArgument = "chicken"
        const val nextPage = "nextPage"
        const val queryArgumentForException = "chicn"
    }
}