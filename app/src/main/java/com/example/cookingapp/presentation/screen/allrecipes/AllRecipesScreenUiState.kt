package com.example.cookingapp.presentation.screen.allrecipes

import com.example.cookingapp.model.SingleMeal

data class AllRecipesScreenUiState(
    val meals: List<SingleMeal> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
