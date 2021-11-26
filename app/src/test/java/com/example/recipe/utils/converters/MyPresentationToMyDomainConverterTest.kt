package com.example.recipe.utils.converters

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class MyPresentationToMyDomainConverterTest {
    private lateinit var myPresentationToMyDomainConverter: Converter<MyRecipePresentationModel, MyRecipeDomainModel>

    @Before
    fun setUp() {
        myPresentationToMyDomainConverter = MyPresentationToMyDomainConverter()
    }

    @Test
    fun testConvert() {
        val testResult = myPresentationToMyDomainConverter.convert(myRecipePresentationModel)
        Truth.assertThat(testResult).isEqualTo(myRecipeDomainModel)
    }

    companion object {
        val myRecipeDomainModel =
            MyRecipeDomainModel("name", "ingredients", "instructions", "image")
        val myRecipePresentationModel =
            MyRecipePresentationModel("name", "ingredients", "instructions", "image")
    }
}