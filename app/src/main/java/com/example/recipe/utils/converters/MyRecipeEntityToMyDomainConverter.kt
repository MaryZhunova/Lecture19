package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.domain.models.MyRecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [MyRecipeEntity] в [MyRecipeDomainModel]
 */
class MyRecipeEntityToMyDomainConverter @Inject constructor() :
    Converter<MyRecipeEntity, MyRecipeDomainModel> {

    override fun convert(from: MyRecipeEntity) =
        MyRecipeDomainModel(
            recipeName = from.recipeName,
            ingredients = from.ingredients,
            instructions = from.instructions,
            image = from.image
        )
}