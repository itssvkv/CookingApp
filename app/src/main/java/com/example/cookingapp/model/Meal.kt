package com.example.cookingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "previous_meals")
data class Meal(
    var strMeal: String? = null,
    var strMealThumb: String? = null,
    @PrimaryKey(autoGenerate = true)
    var idMeal: Int? = null

)
