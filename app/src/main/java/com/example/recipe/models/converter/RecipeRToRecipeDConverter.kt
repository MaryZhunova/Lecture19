package com.example.recipe.models.converter

import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.domain.RecipeD
import com.example.recipe.models.presentation.RecipeP

/**
 * Конвретер из [RecipeR] в [RecipeD]
 */
class RecipeRToRecipeDConverter:Converter<RecipeR, RecipeD> {
    override fun convert(from: RecipeR) =
        RecipeD(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines
        )
}