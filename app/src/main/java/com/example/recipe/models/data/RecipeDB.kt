package com.example.recipe.models.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDB (val uri: String,
                  val label: String,
                  val image: String,
                  val source: String,
                  val url: String,
                  val ingredientLines: List<String>,
                  val dietLabels: List<String>,
                  val healthLabels: List<String>,
                  val cuisineType: List<String>,
                  val mealType: List<String>,
                  val dishType: List<String>,
                  var isFavourite: Boolean) : Parcelable