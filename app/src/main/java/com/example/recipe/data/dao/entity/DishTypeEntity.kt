package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.DishTypeEntry.TABLE_NAME_DISH_TYPES)
data class DishTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.DishTypeEntry.DISH_TYPE)
    val dishType: String
)