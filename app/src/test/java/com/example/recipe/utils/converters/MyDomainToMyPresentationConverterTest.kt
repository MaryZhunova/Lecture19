package com.example.recipe.utils.converters

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class MyDomainToMyPresentationConverterTest {
    private lateinit var domainToPresentationConverter: Converter<MyRecipeDomainModel, MyRecipePresentationModel>

    @Before
    fun setUp() {
        domainToPresentationConverter = MyDomainToMyPresentationConverter()
    }

    @Test
    fun testConvert() {
        val testResult = domainToPresentationConverter.convert(myRecipeDomainModel)
        Truth.assertThat(testResult).isEqualTo(myRecipePresentationModel)
    }

    companion object {
        val myRecipeDomainModel =
            MyRecipeDomainModel("name", "ingredients", "instructions", "image")
        val myRecipePresentationModel =
            MyRecipePresentationModel("name", "ingredients", "instructions", "image")
    }
}