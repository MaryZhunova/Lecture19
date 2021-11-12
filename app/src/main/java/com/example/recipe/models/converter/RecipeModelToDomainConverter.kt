package com.example.recipe.models.converter

import com.example.recipe.models.data.api.RecipeModel
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeModel] в [RecipeDomainModel]
 */
class RecipeModelToDomainConverter @Inject constructor() : Converter<RecipeModel, RecipeDomainModel> {

    override fun convert(from: RecipeModel) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines,
//            dietLabels = from.dietLabels,
//            healthLabels = from.healthLabels,
//            cuisineType = from.cuisineType,
//            mealType = from.mealType,
//            dishType = from.dishType,
            isFavourite = from.isFavourite
        )
}