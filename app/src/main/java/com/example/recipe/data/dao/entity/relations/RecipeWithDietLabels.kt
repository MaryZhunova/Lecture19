package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.DietLabelEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class RecipeWithDietLabels(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "dietLabel",
        associateBy = Junction(RecipeDietLabelCrossRef::class)
    )
    val dietLabels: List<DietLabelEntity>
)