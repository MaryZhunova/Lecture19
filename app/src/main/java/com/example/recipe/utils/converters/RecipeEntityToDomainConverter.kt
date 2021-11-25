package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.domain.models.RecipeDomainModel
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
            isFavourite = from.isFavourite
        )
}