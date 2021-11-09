package com.example.recipe.data.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recipe.models.data.RecipeDB
import java.lang.Exception
import javax.inject.Inject

class RecipesDaoImpl @Inject constructor(private val recipesDbHelper: SQLiteOpenHelper) : RecipesDao {

    override fun getFavouriteRecipes(): List<RecipeDB> {
        val columns = arrayOf(
            RecipesDbContract.RecipesEntry.URI,
            RecipesDbContract.RecipesEntry.LABEL,
            RecipesDbContract.RecipesEntry.IMAGE,
            RecipesDbContract.RecipesEntry.SOURCE,
            RecipesDbContract.RecipesEntry.URL
        )
        val db: SQLiteDatabase = recipesDbHelper.readableDatabase
        val result: MutableList<RecipeDB> = mutableListOf()
        db.query(
            RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES, columns, null, null, null, null, RecipesDbContract.RecipesEntry.LABEL, null
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    RecipeDB(
                        cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.URI)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.LABEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.SOURCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.URL)),
                        true
                    )
                )
            }
        }
        if (result.isEmpty()) throw Exception("No favourite recipes found")
        return result
    }

    override fun addToFavourites(recipe: RecipeDB) {
        val db: SQLiteDatabase = recipesDbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(RecipesDbContract.RecipesEntry.URI, recipe.uri)
        contentValues.put(RecipesDbContract.RecipesEntry.LABEL, recipe.label)
        contentValues.put(RecipesDbContract.RecipesEntry.IMAGE, recipe.image)
        contentValues.put(RecipesDbContract.RecipesEntry.SOURCE, recipe.source)
        contentValues.put(RecipesDbContract.RecipesEntry.URL, recipe.url)

        val newRowId = db.insert(RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES, null, contentValues)
        if (newRowId == (-1).toLong()) {
            throw Exception("Couldn't add to favourites, try again later")
        }
    }

    override fun deleteFromFavourites(recipe: RecipeDB) {
        val db: SQLiteDatabase = recipesDbHelper.writableDatabase
        val selection = RecipesDbContract.RecipesEntry.URI + " LIKE ?"
        val selectionArgs = arrayOf("%${recipe.uri}%")
        db.delete(RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES, selection, selectionArgs)
    }

    override fun isFavourite(recipe: RecipeDB): Boolean {
        val columns = arrayOf(
            RecipesDbContract.RecipesEntry.URI)
        val selection = RecipesDbContract.RecipesEntry.URI + " LIKE ?"
        val selectionArgs = arrayOf("%${recipe.uri}%")
        val db: SQLiteDatabase = recipesDbHelper.readableDatabase
        val result: MutableList<String> = mutableListOf()
        db.query(
            RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES, columns, selection, selectionArgs, null, null, RecipesDbContract.RecipesEntry.LABEL, null
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    cursor.getString(cursor.getColumnIndexOrThrow(RecipesDbContract.RecipesEntry.URI))
                )
            }
        }
        if (result.isEmpty()) return false
        return true
    }
}