package com.example.cookingapp.data.remote.api

import com.example.cookingapp.data.remote.api.dto.CategoriesDto
import com.example.cookingapp.data.remote.api.dto.RandomMealDto
import com.example.cookingapp.utils.Constants.ALL_CATEGORIES
import com.example.cookingapp.utils.Constants.RANDOM_MEAL
import retrofit2.http.GET

interface MealsAPI {
    @GET(ALL_CATEGORIES)
    suspend fun getAllCategories(): CategoriesDto

    @GET(RANDOM_MEAL)
    suspend fun getRandomMeal(): RandomMealDto
}