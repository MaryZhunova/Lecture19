package com.example.recipe.models.converter

import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeDomainModel] в [RecipePresentationModel]
 */
class DomainToPresentationConverter @Inject constructor(): Converter<RecipeDomainModel, RecipePresentationModel> {
    override fun convert(from: RecipeDomainModel) =
        RecipePresentationModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines
        )
}