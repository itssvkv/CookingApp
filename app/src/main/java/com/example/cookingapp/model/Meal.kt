package com.example.cookingapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Meal (
    @SerializedName("strMeal"      ) var strMeal      : String? = null,
    @SerializedName("strMealThumb" ) var strMealThumb : String? = null,
    @SerializedName("idMeal"       ) var idMeal       : String? = null

)
