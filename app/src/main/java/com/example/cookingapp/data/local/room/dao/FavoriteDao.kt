package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: FavoriteMealLocal)

    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAllMeals(): List<FavoriteMealLocal>

    @Query("DELETE FROM favorite_recipes WHERE idMeal=:idMeal")
    suspend fun deleteRecipe(idMeal: Int)
}