package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.data.dao.entity.RecipesDbContract.MyRecipesEntry

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = MyRecipesEntry.TABLE_NAME_MY_RECIPES)
data class MyRecipeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MyRecipesEntry.RECIPE_NAME)
    val recipeName: String,
    @ColumnInfo(name = MyRecipesEntry.INGREDIENTS)
    val ingredients: String,
    @ColumnInfo(name = MyRecipesEntry.INSTRUCTIONS)
    val instructions: String,
    @ColumnInfo(name = MyRecipesEntry.IMAGE)
    val image: String
)





