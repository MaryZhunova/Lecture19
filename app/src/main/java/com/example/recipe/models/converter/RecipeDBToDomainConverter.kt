package com.example.recipe.models.converter

import com.example.recipe.models.data.RecipeDB
import com.example.recipe.models.domain.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeDB] в [RecipeDomainModel]
 */
class RecipeDBToDomainConverter @Inject constructor() :Converter<RecipeDB, RecipeDomainModel> {

    override fun convert(from: RecipeDB) =
        RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            isFavourite = from.isFavourite
        )
}