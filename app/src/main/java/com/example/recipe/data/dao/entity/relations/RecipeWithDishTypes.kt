package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.DishTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithDishTypes(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "dishType",
        associateBy = Junction(RecipeDishTypeCrossRef::class)
    )
    val dishTypes: List<DishTypeEntity>
)