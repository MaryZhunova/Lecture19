package com.example.recipe

import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.domain.RecipeDomainModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class RecipesInteractorTest {
    private val repository: OkhttpRepository = mockk()
    private lateinit var interactor: RecipesInteractor

    @Before
    fun setUp() {
        interactor = RecipesInteractor(repository)
    }

    @Test
    fun getTest() {
        val expectedResult: List<RecipeDomainModel> = mockk()
        every { repository.get(queryArgument) } returns expectedResult

        val testResult = interactor.get(queryArgument)

        Truth.assertThat(testResult).isEqualTo(expectedResult)
    }

    @Test
    fun getTestException() {

        every { repository.get(queryArgumentForException) } throws Exception()

        var testResult = false
        try {
            interactor.get(queryArgumentForException)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    private companion object {
        const val queryArgument = "Nyama"
        const val queryArgumentForException = "nyam"
    }
}