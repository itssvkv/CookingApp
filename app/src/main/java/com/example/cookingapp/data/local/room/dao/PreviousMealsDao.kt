package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealRemote

@Dao
interface PreviousMealsDao {
    @Upsert
    suspend fun insertPreviousRecipe(recipe: Meal)

    @Query("SELECT * FROM previous_meals")
    suspend fun getAllPreviousMeals(): List<Meal>
}