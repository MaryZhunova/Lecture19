package com.example.recipe.data.dao.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.recipe.data.dao.RecipesDbContract
import javax.inject.Inject
import javax.inject.Named

class RecipesDbHelper @Inject constructor(@Named("context") context: Context) :
    SQLiteOpenHelper(context, RecipesDbContract.DB_FILE_NAME, null, RecipesDbContract.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_RECIPES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_RECIPES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val CREATE_TABLE_RECIPES =
            "CREATE TABLE IF NOT EXISTS " + RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES +
                    "(" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RecipesDbContract.RecipesEntry.URI + " TEXT NOT NULL UNIQUE," +
                    RecipesDbContract.RecipesEntry.LABEL + " TEXT," +
                    RecipesDbContract.RecipesEntry.IMAGE + " TEXT," +
                    RecipesDbContract.RecipesEntry.SOURCE + " TEXT," +
                    RecipesDbContract.RecipesEntry.URL + " TEXT" + ")"
        private const val DROP_TABLE_RECIPES =
            "DROP TABLE IF EXISTS " + RecipesDbContract.RecipesEntry.TABLE_NAME_RECIPES
    }
}