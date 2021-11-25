package com.example.recipe.utils.converters

import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [RecipePresentationModel] в [RecipeDomainModel]
 */
class PresentationToDomainConverter @Inject constructor() :
    Converter<RecipePresentationModel, RecipeDomainModel> {
    override fun convert(from: RecipePresentationModel) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines,
            isFavourite = from.isFavourite
        )
}