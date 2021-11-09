package com.example.recipe.models.converter

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeEntity] в [RecipeDomainModel]
 */
class RecipeEntityToDomainConverter @Inject constructor() :
    Converter<RecipeEntity, RecipeDomainModel> {

    override fun convert(from: RecipeEntity) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = emptyList(),
//            dietLabels = from.dietLabels,
//            healthLabels = from.healthLabels,
//            cuisineType = from.cuisineType,
//            mealType = from.mealType,
//            dishType = from.dishType,
            isFavourite = from.isFavourite
        )
}