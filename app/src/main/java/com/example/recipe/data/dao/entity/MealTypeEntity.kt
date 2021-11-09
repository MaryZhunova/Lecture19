package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.MealTypeEntry.TABLE_NAME_MEAL_TYPES)
data class MealTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.MealTypeEntry.MEAL_TYPE)
    val mealType: String
)