package com.example.recipe.utils.converters

import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class DomainToPresentationConverterTest {
    private lateinit var domainToPresentationConverter: Converter<RecipeDomainModel, RecipePresentationModel>

    @Before
    fun setUp() {
        domainToPresentationConverter = DomainToPresentationConverter()
    }
    @Test
    fun testConvert() {
        val testResult = domainToPresentationConverter.convert(recipeDomainModel)
        Truth.assertThat(testResult).isEqualTo(recipePresentationModel)
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