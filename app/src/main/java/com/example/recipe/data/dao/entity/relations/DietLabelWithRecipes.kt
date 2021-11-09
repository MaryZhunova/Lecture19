package com.example.recipe.data.dao.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipe.data.dao.entity.DietLabelEntity
import com.example.recipe.data.dao.entity.RecipeEntity

data class DietLabelWithRecipes(
    @Embedded val dietLabelEntity: DietLabelEntity,
    @Relation(
        parentColumn = "dietLabel",
        entityColumn = "uri",
        associateBy = Junction(RecipeDietLabelCrossRef::class)
    )
    val recipes: List<RecipeEntity>
)