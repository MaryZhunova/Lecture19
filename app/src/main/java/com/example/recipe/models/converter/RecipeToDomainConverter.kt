package com.example.recipe.models.converter

import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.domain.RecipeDomainModel

/**
 * Конвретер из [RecipeR] в [RecipeDomainModel]
 */
class RecipeToDomainConverter:Converter<RecipeR, RecipeDomainModel> {
    override fun convert(from: RecipeR) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines
        )
}