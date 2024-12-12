package com.example.cookingapp.presentation.screen.library

import com.example.cookingapp.model.SingleMealLocal

data class LibraryScreenUiState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val meals: List<SingleMealLocal>? = emptyList(),
    val isMealsReachingTheEnd: Boolean = false,
    val isSearchLoading: Boolean = false,
    val searchResult:  List<SingleMealLocal>? = null
)
