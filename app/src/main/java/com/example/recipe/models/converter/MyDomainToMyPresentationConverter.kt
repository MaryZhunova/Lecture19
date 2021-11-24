package com.example.recipe.models.converter

import com.example.recipe.models.domain.MyRecipeDomainModel
import com.example.recipe.models.presentation.MyRecipePresentationModel
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