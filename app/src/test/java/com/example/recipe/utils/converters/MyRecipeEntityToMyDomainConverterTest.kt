package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * Тест работы конвертера
 */
class MyRecipeEntityToMyDomainConverterTest {
    private lateinit var myRecipeEntityToMyDomainConverter: Converter<MyRecipeEntity, MyRecipeDomainModel>

    @Before
    fun setUp() {
        myRecipeEntityToMyDomainConverter = MyRecipeEntityToMyDomainConverter()
    }

    @Test
    fun testConvert() {
        val testResult = myRecipeEntityToMyDomainConverter.convert(myRecipeEntityModel)
        Truth.assertThat(testResult).isEqualTo(myRecipeDomainModel)
    }

    companion object {
        val myRecipeEntityModel = MyRecipeEntity("name", "ingredients", "instructions", "image")
        val myRecipeDomainModel =
            MyRecipeDomainModel("name", "ingredients", "instructions", "image")
    }
}