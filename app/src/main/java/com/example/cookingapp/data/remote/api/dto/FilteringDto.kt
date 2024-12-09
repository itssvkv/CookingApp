package com.example.cookingapp.data.remote.api.dto


import com.example.cookingapp.model.Meal
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FilteringDto(
    @SerializedName("meals") var meals: List<Meal> = listOf()
)