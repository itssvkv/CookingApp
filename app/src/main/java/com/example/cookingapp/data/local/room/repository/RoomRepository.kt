package com.example.cookingapp.data.local.room.repository

import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.Meal
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
    suspend fun updateRecipe(isFavorite: Boolean, idMeal: Int)


    suspend fun insertPreviousRecipe(recipe: Meal)
    suspend fun getAllPreviousMeals(): Flow<Resource<List<Meal>>>

    suspend fun searchForMeal(searchQuery: String): Flow<Resource<List<SingleMealLocal>>>
    suspend fun searchForMealInFavorite(searchQuery: String):  Flow<Resource<List<FavoriteMealLocal>>>

}