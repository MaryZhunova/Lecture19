package com.example.recipe.models.converter

import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import javax.inject.Inject

/**
 * Конвретер из [RecipePresentationModel] в [RecipeDomainModel]
 */
class PresentationToDomainConverter @Inject constructor(): Converter<RecipePresentationModel, RecipeDomainModel> {
    override fun convert(from: RecipePresentationModel) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
//            ingredientLines = from.ingredientLines,
//            dietLabels = from.dietLabels,
//            healthLabels = from.healthLabels,
//            cuisineType = from.cuisineType,
//            mealType = from.mealType,
//            dishType = from.dishType,
            isFavourite = from.isFavourite
        )
}