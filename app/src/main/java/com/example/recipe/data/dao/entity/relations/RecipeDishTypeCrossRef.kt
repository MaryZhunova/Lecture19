package com.example.recipe.data.dao.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["uri", "dishType"])
data class RecipeDishTypeCrossRef(
    val uri: String,
    val dishType: String
)