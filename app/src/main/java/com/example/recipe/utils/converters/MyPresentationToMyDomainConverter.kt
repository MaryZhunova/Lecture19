package com.example.recipe.utils.converters

import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [MyRecipePresentationModel] в [MyRecipeDomainModel]
 */
class MyPresentationToMyDomainConverter @Inject constructor() :
    Converter<MyRecipePresentationModel, MyRecipeDomainModel> {
    override fun convert(from: MyRecipePresentationModel) =
        MyRecipeDomainModel(
            recipeName = from.recipeName,
            ingredients = from.ingredients,
            instructions = from.instructions,
            image = from.image
        )
}