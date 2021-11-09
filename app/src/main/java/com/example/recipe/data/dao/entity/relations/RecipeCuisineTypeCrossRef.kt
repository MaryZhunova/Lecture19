package com.example.recipe.data.dao.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["uri", "cuisineType"])
data class RecipeCuisineTypeCrossRef(
    val uri: String,
    val cuisineType: String
)