package com.example.cookingapp.presentation.screen.home

import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.SingleMeal

data class HomeScreenUiState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val categories: HomeCategoriesResponse? = null,
    val isFocused: Boolean = false,
    val meals: List<SingleMeal> = emptyList(),
    val isLoadingMoreMeals: Boolean = false,
    val isMealsReachingTheEnd: Boolean = false
)

