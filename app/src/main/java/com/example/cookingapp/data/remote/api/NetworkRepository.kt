package com.example.cookingapp.data.remote.api

import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun getAllCategories(): Flow<Resource<HomeCategoriesResponse>>

    suspend fun getRandomMeal(): Flow<Resource<List<SingleMealLocal>>>

    suspend fun getAllMealsWithMainIngredient(ingredient: String): Flow<Resource<List<Meal>>>
    suspend fun getAllMealsWithMainCategory(category: String): Flow<Resource<List<Meal>>>
    suspend fun getAllMealsWithMainArea(area: String): Flow<Resource<List<Meal>>>
    suspend fun getMealInfoById(id: String): Flow<Resource<SingleMealLocal>>

}