package com.example.recipe

import com.example.recipe.utils.Constants
import com.example.recipe.data.api.RecipesApiImpl
import com.example.recipe.models.data.api.RecipeModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.Before
import java.lang.Exception

class RecipesApiImplTest {

    private lateinit var url: HttpUrl
    private lateinit var api: RecipesApiImpl
    private val okHttpClient: OkHttpClient = mockk()

    @Before
    fun setUp() {
        url = Constants.URL.toHttpUrl()
        api = RecipesApiImpl(okHttpClient, url)
    }

    @Test
    fun getTest() {
        val expectedJson = "{\n" +
                "  \"from\": 0,\n" +
                "  \"to\": 0,\n" +
                "  \"count\": 0,\n" +
                "  \"hits\": [\n" +
                "    {\n" +
                "      \"recipeModel\": {\n" +
                "        \"uri\": \"uri\",\n" +
                "        \"label\": \"label\",\n" +
                "        \"image\": \"image\",\n" +
                "        \"source\": \"source\",\n" +
                "        \"url\": \"url\",\n" +
                "        \"dietLabels\": [\n" +
                "          \"dietLabels\"\n" +
                "        ],\n" +
                "        \"healthLabels\": [\n" +
                "          \"healthLabels\"\n" +
                "        ],\n" +
                "        \"ingredientLines\": [\n" +
                "          \"ingredientLines\"\n" +
                "        ],\n" +
                "        \"cuisineType\": [\n" +
                "          \"cuisineType\"\n" +
                "        ],\n" +
                "        \"mealType\": [\n" +
                "          \"mealType\"\n" +
                "        ],\n" +
                "        \"dishType\": [\n" +
                "          \"dishType\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val responseBody = expectedJson.toResponseBody("application/json".toMediaType())
        val response = Response.Builder()
            .code(200)
            .request(
                Request.Builder()
                .url("https://api.edamam.com/api/recipes/v2?type=public&q=Nyama&app_id=f2aed758&app_key=e906d60b053299bd251af78fa297f507")
                .build())
            .message("")
            .body(responseBody)
            .protocol(Protocol.HTTP_2)
            .build()
        val expectedResult = listOf(
            RecipeModel("uri", "label", "image", "source",
            "url", listOf("ingredientLines"), listOf("dietLabels"), listOf("healthLabels"), listOf("cuisineType"), listOf("mealType"), listOf("dishType"))
        )
        every { okHttpClient.newCall(any()).execute() } returns response

        val testResult = api.get(queryArgument)

        Truth.assertThat(testResult).isEqualTo(expectedResult)
    }

    @Test
    fun getTestException() {
        var testResult = false

        try {
            api.get(queryArgumentForException)
        } catch (e: Exception) {
            testResult = true
        }

        Truth.assertThat(testResult).isTrue()
    }

    private companion object {
        const val queryArgument = "Nyama"
        const val queryArgumentForException = "Nyam"
    }
}

