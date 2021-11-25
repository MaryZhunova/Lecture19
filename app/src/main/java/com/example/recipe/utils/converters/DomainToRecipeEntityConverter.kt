package com.example.recipe.utils.converters

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.domain.models.RecipeDomainModel
import javax.inject.Inject

/**
 * Конвретер из [RecipeDomainModel] в [RecipeEntity]
 */
class DomainToRecipeEntityConverter @Inject constructor() :
    Converter<RecipeDomainModel, RecipeEntity> {

    override fun convert(from: RecipeDomainModel) =
        RecipeEntity(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            isFavourite = from.isFavourite
        )
}