package com.example.recipe.utils.converters

import com.example.recipe.data.network.models.Recipe
import com.example.recipe.domain.models.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [Recipe] в [RecipeDomainModel]
 */
class RecipeToDomainConverter @Inject constructor() : Converter<Recipe, RecipeDomainModel> {

    override fun convert(from: Recipe): RecipeDomainModel {
        return RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines,
            isFavourite = from.isFavourite
        )
    }
}