package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.HealthLabelEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class HealthLabelWithRecipes(
    @Embedded val healthLabelEntity: HealthLabelEntity,
    @Relation(
        parentColumn = "healthLabel",
        entityColumn = "uri",
        associateBy = Junction(RecipeHealthLabelCrossRef::class)
    )
    val recipes: List<RecipeEntity>
)