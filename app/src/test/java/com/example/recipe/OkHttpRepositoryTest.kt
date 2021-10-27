package com.example.recipe

import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.model.Recipe
import com.example.recipe.data.repository.OkhttpRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.Before
import java.lang.Exception

class OkHttpRepositoryTest {
    var api: OkHttpRecipesApiImpl = mockk()
    lateinit var repository: OkhttpRepository

    @Before
    fun setUp() {
        repository = OkhttpRepository(api)
    }

    @Test
    fun getTest() {
        val expectedResult: List<Recipe> = mockk()
        every { api.get(queryArgument) } returns expectedResult

        val testResult = repository.get(queryArgument)

        Truth.assertThat(testResult).isEqualTo(expectedResult)
    }

    @Test
    fun getTestException() {

        every { api.get(queryArgumentForException) } throws Exception()

        var testResult = false
        try {
            repository.get(queryArgumentForException)
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