package com.example.recipe.data.dao.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["uri", "healthLabel"])
data class RecipeHealthLabelCrossRef(
    val uri: String,
    val healthLabel: String
)