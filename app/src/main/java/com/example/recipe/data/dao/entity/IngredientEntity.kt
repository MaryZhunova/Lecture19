package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.IngredientLinesEntry.TABLE_NAME_INGREDIENTS)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.IngredientLinesEntry.INGREDIENT)
    val ingredient: String,
    val uri: String
)