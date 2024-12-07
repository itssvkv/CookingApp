package com.example.cookingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "all_recipes")
data class SingleMealRemote(
    @PrimaryKey(autoGenerate = true)
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
    val isFavorite: Boolean = false,
    val favIndexesList: List<Int?> = emptyList(),
    val lastUpdated: Long = System.currentTimeMillis()
)