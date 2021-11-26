package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class MyDomainToMyRecipeEntityConverterTest {
    private lateinit var domainToRecipeEntityConverter: Converter<MyRecipeDomainModel, MyRecipeEntity>

    @Before
    fun setUp() {
        domainToRecipeEntityConverter = MyDomainToMyRecipeEntityConverter()
    }

    @Test
    fun testConvert() {
        val testResult = domainToRecipeEntityConverter.convert(myRecipeDomainModel)
        Truth.assertThat(testResult).isEqualTo(myRecipeEntityModel)
    }

    companion object {
        val myRecipeEntityModel = MyRecipeEntity("name", "ingredients", "instructions", "image")
        val myRecipeDomainModel =
            MyRecipeDomainModel("name", "ingredients", "instructions", "image")
    }
}