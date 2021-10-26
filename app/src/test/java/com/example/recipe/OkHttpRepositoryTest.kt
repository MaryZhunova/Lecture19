package com.example.recipe

import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.model.Recipe
import com.example.recipe.data.repository.OkhttpRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OkHttpRepositoryTest {
    private lateinit var api: RecipesApi
    private lateinit var repository: OkhttpRepository
    private val expectedResult: List<Recipe> = mockk()
    private val queryArgument = "cheese"



    @Before
    fun setUp() {
        api = mockk()
        repository = OkhttpRepository(api)
    }

    @Test
    fun get_isCorrect() {
        every { api.get(queryArgument) } returns expectedResult

        val testSubscriber = repository.get(queryArgument)

    }
}