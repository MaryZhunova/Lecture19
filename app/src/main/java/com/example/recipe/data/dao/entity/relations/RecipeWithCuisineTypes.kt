package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.CuisineTypeEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithCuisineTypes(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "cuisineType",
        associateBy = Junction(RecipeCuisineTypeCrossRef::class)
    )
    val cuisineTypes: List<CuisineTypeEntity>
)