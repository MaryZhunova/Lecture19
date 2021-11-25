package com.example.recipe.utils.converters

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [MyRecipeDomainModel] в [MyRecipePresentationModel]
 */
class MyDomainToMyPresentationConverter @Inject constructor() :
    Converter<MyRecipeDomainModel, MyRecipePresentationModel> {
    override fun convert(from: MyRecipeDomainModel) =
        MyRecipePresentationModel(
            recipeName = from.recipeName,
            ingredients = from.ingredients,
            instructions = from.instructions,
            image = from.image
        )
}