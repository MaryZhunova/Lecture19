package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.HealthLabelsEntry.TABLE_NAME_HEALTH_LABELS)
data class HealthLabelEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.HealthLabelsEntry.HEALTH_LABEL)
    val healthLabel: String
)