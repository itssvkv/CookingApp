package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote

@Dao
interface AllRecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: SingleMealRemote)

    @Query("SELECT * FROM all_recipes")
    suspend fun getAllMeals(): List<SingleMealRemote>

}