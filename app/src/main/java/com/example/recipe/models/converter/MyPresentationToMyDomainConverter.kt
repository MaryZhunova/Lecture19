package com.example.recipe.models.converter

import com.example.recipe.models.domain.MyRecipeDomainModel
import com.example.recipe.models.presentation.MyRecipePresentationModel
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