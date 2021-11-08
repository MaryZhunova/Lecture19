package com.example.recipe.data.db

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recipe.models.data.RecipeDB
import javax.inject.Inject

class RecipesDbImpl @Inject constructor(private val recipesDbHelper: SQLiteOpenHelper) : RecipesDb {
    @SuppressLint("Range")
    override fun getFavouriteRecipes(): List<RecipeDB> {
        val columns = arrayOf(
            RecipesDbContract.RecipesEntry.URI,
            RecipesDbContract.RecipesEntry.LABEL,
            RecipesDbContract.RecipesEntry.IMAGE,
            RecipesDbContract.RecipesEntry.SOURCE,
            RecipesDbContract.RecipesEntry.URL,
            RecipesDbContract.RecipesEntry.INGREDIENT_LINES,
            RecipesDbContract.RecipesEntry.DIET_LABELS,
            RecipesDbContract.RecipesEntry.HEALTH_LABELS,
            RecipesDbContract.RecipesEntry.CUISINE_TYPE,
            RecipesDbContract.RecipesEntry.MEAL_TYPE,
            RecipesDbContract.RecipesEntry.DISH_TYPE
        )
        val db: SQLiteDatabase = recipesDbHelper.readableDatabase
        val result: MutableList<RecipeDB> = mutableListOf()
        db.query(
            RecipesDbContract.RecipesEntry.TABLE_NAME, columns, null, null, null, null, RecipesDbContract.RecipesEntry.LABEL, null
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    RecipeDB(
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.URI)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.LABEL)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.SOURCE)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.URL)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.INGREDIENT_LINES)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.DIET_LABELS)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.HEALTH_LABELS)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.CUISINE_TYPE)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.MEAL_TYPE)),
                        cursor.getString(cursor.getColumnIndex(RecipesDbContract.RecipesEntry.DISH_TYPE)),
                        true
                    )
                )
            }
        }
        return result
    }
}