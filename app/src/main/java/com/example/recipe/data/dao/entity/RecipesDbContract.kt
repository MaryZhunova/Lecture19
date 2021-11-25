package com.example.recipe.data.dao.entity

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
            const val IS_FAVOURITE = "isFavourite"
        }
    }

    interface IngredientLinesEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_INGREDIENTS = "ingredients"
            const val INGREDIENT = "ingredient"
        }
    }

    interface MyRecipesEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_MY_RECIPES = "myRecipes"
            const val RECIPE_NAME = "recipeName"
            const val INGREDIENTS = "ingredients"
            const val INSTRUCTIONS = "instructions"
            const val IMAGE = "image"
        }
    }
}
