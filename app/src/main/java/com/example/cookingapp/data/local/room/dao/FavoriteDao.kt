package com.example.cookingapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookingapp.model.FavoriteMealLocal
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: FavoriteMealLocal)

    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAllMeals(): List<FavoriteMealLocal>

    @Query("DELETE FROM favorite_recipes WHERE idMeal=:idMeal")
    suspend fun deleteRecipe(idMeal: Int)

    @Query("SELECT * FROM favorite_recipes WHERE strMeal LIKE '%' || :searchQuery || '%' OR strArea LIKE '%' || :searchQuery || '%' OR strCategory LIKE '%' || :searchQuery || '%'" )
    suspend fun searchForMealInFavorite(searchQuery: String):  List<FavoriteMealLocal>
}