package com.example.cookingapp.presentation.screen.newrecipe

import kotlinx.serialization.Serializable

@Serializable
data class NewRecipeScreenUiState(
    var idMeal: Int? = null,
    var strMeal: String? = null,
    var strDrinkAlternate: String? = null,
    var strCategory: String? = null,
    var strArea: String? = null,
    var strInstructions: String? = null,
    var strMealThumb: String? = null,
    var strTags: String? = null,
    var strYoutube: String? = null,
    var strSource: String? = null,
    val ingredient: List<String?> = emptyList(),
    val measure: List<String?> = emptyList(),
    val prepTime: Int = 0,
    val cookTime: Int = 0,
    val totalTime: Int = prepTime + cookTime,
    val recipeImageFormDevice: String = "",
    val ingredientsImagesFromDevice: List<String?> = emptyList(),
    val isFavorite: Boolean = false

)
