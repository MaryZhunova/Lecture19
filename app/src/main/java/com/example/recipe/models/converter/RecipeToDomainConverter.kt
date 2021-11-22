package com.example.recipe.models.converter

import com.example.recipe.data.repository.RecipesRepositoryImpl
import com.example.recipe.models.data.api.Recipe
import com.example.recipe.models.data.api.RecipeResponse
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.domain.RecipeDomainModel2
import javax.inject.Inject

/**
 * Конвретер из [RecipeModel] в [RecipeDomainModel]
 */
class RecipeToDomainConverter @Inject constructor() : Converter<RecipeResponse, RecipeDomainModel2> {

    override fun convert(from: RecipeResponse): RecipeDomainModel2 {
        RecipesRepositoryImpl.next = from.link.next.href.substringAfter("_cont=").substringBefore("&type")
        return RecipeDomainModel2(
            next = from.link.next.href.substringAfter("_cont=").substringBefore("&type"),
            recipes = from.hits.map { recipe -> recipe.recipe }.map { recipe -> hitConvert(recipe) }
        )
    }

    private fun hitConvert(from: Recipe) : RecipeDomainModel {
        return RecipeDomainModel(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
            ingredientLines = from.ingredientLines,
//            dietLabels = from.dietLabels,
//            healthLabels = from.healthLabels,
//            cuisineType = from.cuisineType,
//            mealType = from.mealType,
//            dishType = from.dishType,
            isFavourite = from.isFavourite
        )
    }
}