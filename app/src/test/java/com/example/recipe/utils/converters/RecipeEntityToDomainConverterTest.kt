package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.domain.models.RecipeDomainModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class RecipeEntityToDomainConverterTest {
    private lateinit var recipeEntityToDomainConverter: Converter<RecipeEntity, RecipeDomainModel>

    @Before
    fun setUp() {
        recipeEntityToDomainConverter = RecipeEntityToDomainConverter()
    }
    @Test
    fun testConvert() {
        val testResult = recipeEntityToDomainConverter.convert(recipeEntity)
        Truth.assertThat(testResult).isEqualTo(recipeDomainModel)
    }

    companion object {
        val recipeEntity = RecipeEntity(
            "uri", "label", "image", "source",
            "url", true
        )
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", emptyList(), true
        )
    }
}