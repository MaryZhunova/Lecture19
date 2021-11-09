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

    interface DietLabelsEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_DIET_LABELS = "dietLabels"
            const val DIET_LABEL = "dietLabel"
        }
    }

    interface HealthLabelsEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_HEALTH_LABELS = "healthLabels"
            const val HEALTH_LABEL = "healthLabel"
        }
    }

    interface CuisineTypeEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_CUISINE_TYPES = "cuisineTypes"
            const val CUISINE_TYPE = "ingredient"
        }
    }

    interface MealTypeEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_MEAL_TYPES = "mealTypes"
            const val MEAL_TYPE = "mealType"
        }
    }

    interface DishTypeEntry : BaseColumns {
        companion object {
            const val TABLE_NAME_DISH_TYPES = "dishTypes"
            const val DISH_TYPE = "dishType"
        }
    }
}
