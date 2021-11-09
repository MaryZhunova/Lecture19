package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.MealTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class MealTypeWithRecipes(
    @Embedded val mealTypeEntity: MealTypeEntity,
    @Relation(
        parentColumn = "mealType",
        entityColumn = "uri",
        associateBy = Junction(RecipeMealTypeCrossRef::class)
    )
    val recipes: List<RecipeEntity>
)