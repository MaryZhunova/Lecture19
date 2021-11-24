package com.example.recipe.data.dao.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.data.dao.entity.RecipesDbContract

/**
 * Класс для создания базы данных по шаблону singleton
 */
@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        MyRecipeEntity::class
    ],
    version = RecipesDbContract.DB_VERSION
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract val recipesDao: RecipesDao

    companion object {
        private var INSTANCE: RecipesDatabase? = null

        /**
         * Создание экземпляра класса
         *
         * @param context контекст
         * @return RecipesDatabase база данных
         */
        fun getInstance(context: Context): RecipesDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                RecipesDatabase::class.java,
                RecipesDbContract.DB_FILE_NAME
            ).allowMainThreadQueries().build().also { INSTANCE = it }
        }
    }

}