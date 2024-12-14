package com.example.cookingapp.data.remote.api.dto


import com.example.cookingapp.model.Meal
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FilteringDto(
    @SerializedName("meals") var meals: List<SomeMeal> = listOf()
) {
    @Serializable
    data class SomeMeal(
        var strMeal: String? = null,
        var strMealThumb: String? = null,
        var idMeal: String? = null
    )


}