package com.example.recipe.data.dao.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["uri", "mealType"])
data class RecipeMealTypeCrossRef(
    val uri: String,
    val mealType: String
)