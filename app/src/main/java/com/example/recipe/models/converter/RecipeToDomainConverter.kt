package com.example.recipe.models.converter

import com.example.recipe.models.data.api.Recipe
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [Recipe] в [RecipeDomainModel]
 */
class RecipeToDomainConverter @Inject constructor() : Converter<Recipe, RecipeDomainModel> {

    override fun convert(from: Recipe) =
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