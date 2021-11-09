package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesDbContract.DietLabelsEntry.TABLE_NAME_DIET_LABELS)
data class DietLabelEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesDbContract.DietLabelsEntry.DIET_LABEL)
    val dietLabel: String
)