package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookingapp.model.SingleMealLocal

@Dao
interface MyRecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: SingleMealLocal)

    @Query("SELECT * FROM my_recipes")
    suspend fun getAllMeals(): List<SingleMealLocal>
    @Query("SELECT * FROM my_recipes WHERE strMeal LIKE '%' || :searchQuery || '%' OR strArea LIKE '%' || :searchQuery || '%' OR strCategory LIKE '%' || :searchQuery || '%'" )
    suspend fun searchForMeal(searchQuery: String):  List<SingleMealLocal>


}