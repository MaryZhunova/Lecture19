package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.DishTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class DishTypeWithRecipes(
    @Embedded val dishTypeEntity: DishTypeEntity,
    @Relation(
        parentColumn = "dishType",
        entityColumn = "uri",
        associateBy = Junction(RecipeDishTypeCrossRef::class)
    )
    val recipes: List<RecipeEntity>
)