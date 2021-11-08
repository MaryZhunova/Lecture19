package com.example.recipe.models.converter

import com.example.recipe.models.data.RecipeDB
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeDomainModel] в [RecipeDB]
 */
class DomainToRecipeDBConverter @Inject constructor(): Converter<RecipeDomainModel, RecipeDB> {

    override fun convert(from: RecipeDomainModel) =
        RecipeDB(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            isFavourite = from.isFavourite
        )
}