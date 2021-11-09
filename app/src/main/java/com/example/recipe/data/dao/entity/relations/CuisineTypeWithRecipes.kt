package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.CuisineTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class CuisineTypeWithRecipes(
    @Embedded val cuisineTypeEntity: CuisineTypeEntity,
    @Relation(
        parentColumn = "cuisineType",
        entityColumn = "uri",
        associateBy = Junction(RecipeCuisineTypeCrossRef::class)
    )
    val recipes: List<RecipeEntity>
)