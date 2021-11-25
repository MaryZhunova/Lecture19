package com.example.recipe.utils.converters

import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeDomainModel] в [RecipePresentationModel]
 */
class DomainToPresentationConverter @Inject constructor() :
    Converter<RecipeDomainModel, RecipePresentationModel> {
    override fun convert(from: RecipeDomainModel) =
        RecipePresentationModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines,
            isFavourite = from.isFavourite
        )
}