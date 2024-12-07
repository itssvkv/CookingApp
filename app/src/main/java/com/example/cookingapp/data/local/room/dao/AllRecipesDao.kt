package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote

@Dao
interface AllRecipesDao {
    @Upsert
    suspend fun insertRecipe(recipe: SingleMealRemote)

    @Query("SELECT * FROM all_recipes")
    suspend fun getAllMeals(): List<SingleMealRemote>

    @Query("UPDATE all_recipes SET isFavorite = :isFavorite WHERE idMeal = :idMeal")
    suspend fun updateRecipe(isFavorite: Boolean, idMeal: Int)
}