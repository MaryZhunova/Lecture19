package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.CuisineTypeEntry.TABLE_NAME_CUISINE_TYPES)
data class CuisineTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.CuisineTypeEntry.CUISINE_TYPE)
    val cuisineType: String
)