package com.example.recipe.data.dao.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["uri", "dietLabel"])
data class RecipeDietLabelCrossRef(
                        val uri: String,
                        val dietLabel: String
                        )