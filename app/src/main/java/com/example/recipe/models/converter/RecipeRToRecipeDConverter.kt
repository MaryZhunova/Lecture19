package com.example.recipe.models.converter

import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.domain.RecipeD
import javax.inject.Inject

/**
 * Конвретер из [RecipeR] в [RecipeD]
 */
class RecipeRToRecipeDConverter:Converter<RecipeR, RecipeD> {

    @Inject
    constructor()

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