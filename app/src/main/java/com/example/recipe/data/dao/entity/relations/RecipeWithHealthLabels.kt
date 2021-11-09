package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.HealthLabelEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithHealthLabels(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
                            parentColumn = "uri",
                            entityColumn = "healthLabel",
                            associateBy = Junction(RecipeHealthLabelCrossRef::class)
                        )
                        val healthLabels: List<HealthLabelEntity>
                    )