package com.example.cookingapp.data.local.room.repository

import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun insertRecipe(recipe: SingleMealLocal)
    suspend fun getAllMeals(): Flow<Resource<List<SingleMealLocal>>>

    suspend fun insertRecipeToFavorite(recipe: FavoriteMealLocal)
    suspend fun getAllFavoriteMeals(): Flow<Resource<List<FavoriteMealLocal>>>
    suspend fun deleteRecipeFromFavorite(idMeal: Int)


    suspend fun insertRecipeToCache(
        onLoading: () -> Unit,
        onFailure: () -> Unit,
        onSuccess: () -> Unit

    )

    suspend fun getAllMealsFromCache(): Flow<Resource<List<SingleMealRemote>>>
}