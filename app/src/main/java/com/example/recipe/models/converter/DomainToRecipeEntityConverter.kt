package com.example.recipe.models.converter

import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.models.domain.RecipeDomainModel
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