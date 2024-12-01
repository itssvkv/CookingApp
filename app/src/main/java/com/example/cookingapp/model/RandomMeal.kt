package com.example.cookingapp.model

import com.google.gson.annotations.SerializedName

data class RandomMeal(
    var idMeal: String? = null,
    var strMeal: String? = null,
    var strDrinkAlternate: String? = null,
    var strCategory: String? = null,
    var strArea: String? = null,
    var strInstructions: String? = null,
    var strMealThumb: String? = null,
    var strTags: String? = null,
    var strYoutube: String? = null,
    val ingredient: List<String?> = emptyList(),
    val measure: List<String?> = emptyList(),
    val prepTime: Int = 0,
    val cookTime: Int = 0,
    val totalTime: Int = 0

)
