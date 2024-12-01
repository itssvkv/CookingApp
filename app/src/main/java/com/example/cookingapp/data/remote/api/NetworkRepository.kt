package com.example.cookingapp.data.remote.api

import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun getAllCategories(): Flow<Resource<HomeCategoriesResponse>>

    suspend fun getRandomMeal(): Flow<Resource<List<SingleMealLocal>>>

}