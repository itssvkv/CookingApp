package com.example.cookingapp.presentation.screen.allrecipes

import com.example.cookingapp.model.SingleMealLocal


data class AllRecipesScreenUiState(
    val meals: List<SingleMealLocal> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
