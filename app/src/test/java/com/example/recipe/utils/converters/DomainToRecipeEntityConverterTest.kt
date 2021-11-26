package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.domain.models.RecipeDomainModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class DomainToRecipeEntityConverterTest {
    private lateinit var domainToRecipeEntityConverter: Converter<RecipeDomainModel, RecipeEntity>

    @Before
    fun setUp() {
        domainToRecipeEntityConverter = DomainToRecipeEntityConverter()
    }
    @Test
    fun testConvert() {
        val testResult = domainToRecipeEntityConverter.convert(recipeDomainModel)
        Truth.assertThat(testResult).isEqualTo(recipeEntity)
    }

    companion object {
        val recipeEntity = RecipeEntity(
            "uri", "label", "image", "source",
            "url", true
        )
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
    }
}