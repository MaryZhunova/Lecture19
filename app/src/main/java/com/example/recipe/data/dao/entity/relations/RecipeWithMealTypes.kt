package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.MealTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithMealTypes(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "mealType",
        associateBy = Junction(RecipeMealTypeCrossRef::class)
    )
    val mealTypes: List<MealTypeEntity>
)