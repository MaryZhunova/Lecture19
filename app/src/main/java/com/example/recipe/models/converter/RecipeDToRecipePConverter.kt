package com.example.recipe.models.converter

import com.example.recipe.models.domain.RecipeD
import com.example.recipe.models.presentation.RecipeP

/**
 * Конвретер из [RecipeD] в [RecipeP]
 */
class RecipeDToRecipePConverter:Converter<RecipeD, RecipeP> {
    override fun convert(from: RecipeD) =
        RecipeP(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines
        )
}