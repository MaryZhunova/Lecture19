package com.example.recipe.data.db

import android.provider.BaseColumns

interface RecipesDbContract {

    companion object {
        const val DB_FILE_NAME = "recipes.db"
        const val DB_VERSION = 1
    }

    interface RecipesEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "recipes"
            const val URI = "uri"
            const val LABEL = "label"
            const val IMAGE = "image"
            const val SOURCE = "source"
            const val URL = "url"
            const val INGREDIENT_LINES = "ingredientLines"
            const val DIET_LABELS = "dietLabels"
            const val HEALTH_LABELS = "healthLabels"
            const val CUISINE_TYPE = "cuisineType"
            const val MEAL_TYPE = "mealType"
            const val DISH_TYPE = "dishType"
        }
    }
}