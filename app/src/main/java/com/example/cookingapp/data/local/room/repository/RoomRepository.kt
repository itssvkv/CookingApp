package com.example.cookingapp.data.local.room.repository

import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun insertRecipe(recipe: SingleMealLocal)
    suspend fun getAllMeals(): Flow<Resource<List<SingleMealLocal>>>
}