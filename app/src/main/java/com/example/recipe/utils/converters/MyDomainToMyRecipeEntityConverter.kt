package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.domain.models.MyRecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [MyRecipeDomainModel] в [MyRecipeEntity]
 */
class MyDomainToMyRecipeEntityConverter @Inject constructor() :
    Converter<MyRecipeDomainModel, MyRecipeEntity> {

    override fun convert(from: MyRecipeDomainModel) =
        MyRecipeEntity(
            recipeName = from.recipeName,
            ingredients = from.ingredients,
            instructions = from.instructions,
            image = from.image
        )
}