package com.example.recipe.utils.converters

import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class PresentationToDomainConverterTest {
    private lateinit var presentationToDomainConverter: Converter<RecipePresentationModel, RecipeDomainModel>

    @Before
    fun setUp() {
        presentationToDomainConverter = PresentationToDomainConverter()
    }
    @Test
    fun testConvert() {
        val testResult = presentationToDomainConverter.convert(recipePresentationModel)
        Truth.assertThat(testResult).isEqualTo(recipeDomainModel)
    }

    companion object {
        val recipePresentationModel = RecipePresentationModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
    }
}