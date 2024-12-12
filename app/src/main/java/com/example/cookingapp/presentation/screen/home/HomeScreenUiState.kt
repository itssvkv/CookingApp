package com.example.cookingapp.presentation.screen.home

import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.SingleMealLocal

data class HomeScreenUiState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val categories: HomeCategoriesResponse? = null,
    val isFocused: Boolean = false,
    val meals: List<SingleMealLocal> = emptyList(),
    val isLoadingMoreMeals: Boolean = false,
    val isMealsReachingTheEnd: Boolean = false,
    val isOneCategoryClick: Boolean = false,
    val categoryIndex: Int? = null,
    val isCategoryLoading: Boolean = false,
    val categoryMeals: List<SingleMealLocal> = emptyList(),
    val isSearchLoading: Boolean = false,
    val searchResult:  List<SingleMealLocal>? = null

)

