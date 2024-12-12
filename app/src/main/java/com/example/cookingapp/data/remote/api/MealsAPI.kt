package com.example.cookingapp.data.remote.api

import com.example.cookingapp.data.remote.api.dto.CategoriesDto
import com.example.cookingapp.data.remote.api.dto.FilteringDto
import com.example.cookingapp.data.remote.api.dto.RandomMealDto
import com.example.cookingapp.utils.Constants.ALL_CATEGORIES
import com.example.cookingapp.utils.Constants.FILTER_INGREDIENT
import com.example.cookingapp.utils.Constants.MEAL_BY_ID
import com.example.cookingapp.utils.Constants.RANDOM_MEAL
import com.example.cookingapp.utils.Constants.SEARCH_MEAL_BY_NAME
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsAPI {
    @GET(ALL_CATEGORIES)
    suspend fun getAllCategories(): CategoriesDto

    @GET(RANDOM_MEAL)
    suspend fun getRandomMeal(): RandomMealDto

    @GET(FILTER_INGREDIENT)
    suspend fun getAllMealsWithMainIngredient(
        @Query("i")
        ingredient: String
    ): FilteringDto

    @GET(FILTER_INGREDIENT)
    suspend fun getAllMealsWithCategory(
        @Query("c")
        category: String
    ): FilteringDto

    @GET(FILTER_INGREDIENT)
    suspend fun getAllMealsWithArea(
        @Query("a")
        area: String
    ): FilteringDto

    @GET(MEAL_BY_ID)
    suspend fun getMealInfoById(@Query("i") id: String): RandomMealDto

    @GET(SEARCH_MEAL_BY_NAME)
    suspend fun searchForMealByName(@Query("s") searchQuery: String): RandomMealDto
}