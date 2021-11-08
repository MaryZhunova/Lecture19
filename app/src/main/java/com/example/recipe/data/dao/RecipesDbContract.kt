package com.example.recipe.data.dao

import android.provider.BaseColumns

interface RecipesDbContract {

    companion object {
        const val DB_FILE_NAME = "recipes.db"
        const val DB_VERSION = 1
    }

    interface RecipesEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_RECIPES = "recipes"
            const val URI = "uri"
            const val LABEL = "label"
            const val IMAGE = "image"
            const val SOURCE = "source"
            const val URL = "url"
        }
    }
}