package com.example.recipe.models.converter

import com.example.recipe.models.data.Recipe
import com.example.recipe.models.data.RecipeDB
import javax.inject.Inject

/**
 * Конвретер из [Recipe] в [RecipeDB]
 */
class RecipeToRecipeDBConverter @Inject constructor() :Converter<Recipe, RecipeDB> {

    override fun convert(from: Recipe) =
        RecipeDB(
            uri = from.uri,
            label = from.label,
            image = from.image,
            source = from.source,
            url = from.url,
//            ingredientLines = from.ingredientLines,
//            dietLabels = from.dietLabels,
//            healthLabels = from.healthLabels,
//            cuisineType = from.cuisineType,
//            mealType = from.mealType,
//            dishType = from.dishType,
            isFavourite = from.isFavourite
        )
}