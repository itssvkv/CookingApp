package com.example.cookingapp.data.remote.api.dto


import androidx.room.PrimaryKey
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

    fun toMeal(): Meal {
        return Meal(
            strMeal = this.meals[0].strMeal,
            strMealThumb = this.meals[0].strMealThumb,
            idMeal = this.meals[0].idMeal?.toInt()
        )
    }
}