package com.example.cookingapp.data.remote.api

import androidx.compose.foundation.pager.PageSize
import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.RandomMeal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun getAllCategories(): Flow<Resource<HomeCategoriesResponse>>

    suspend fun getRandomMeal(): Flow<Resource<List<RandomMeal>>>

}