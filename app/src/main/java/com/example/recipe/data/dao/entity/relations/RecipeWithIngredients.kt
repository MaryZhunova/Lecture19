package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithIngredients(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "uri"
    )
    val ingredients: List<IngredientEntity>
)