package com.example.recipe.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.data.dao.entity.RecipesDbContract.RecipesEntry

/**
 * Entity класс для создания таблицы
 */
@Entity(tableName = RecipesEntry.TABLE_NAME_RECIPES)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RecipesEntry.URI)
    val uri: String,
    @ColumnInfo(name = RecipesEntry.LABEL)
    val label: String,
    @ColumnInfo(name = RecipesEntry.IMAGE)
    val image: String,
    @ColumnInfo(name = RecipesEntry.SOURCE)
    val source: String,
    @ColumnInfo(name = RecipesEntry.URL)
    val url: String,
    @ColumnInfo(name = RecipesEntry.IS_FAVOURITE)
    var isFavourite: Boolean = true
)





