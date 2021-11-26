package com.example.recipe.utils.converters

import com.example.recipe.data.network.models.Recipe
import com.example.recipe.domain.models.RecipeDomainModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class RecipeToDomainConverterTest {
    private lateinit var recipeToDomainConverter: Converter<Recipe, RecipeDomainModel>

    @Before
    fun setUp() {
        recipeToDomainConverter = RecipeToDomainConverter()
    }
    @Test
    fun testConvert() {
        val testResult = recipeToDomainConverter.convert(recipe)
        Truth.assertThat(testResult).isEqualTo(recipeDomainModel)
    }

    companion object {
        val recipe = Recipe(
            "uri", "label", "image", "source",
            "url", listOf("ingredients")
        )
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredients"), false
        )
    }
}