package com.example.cookingapp.presentation.screen.allrecipes

import com.example.cookingapp.model.RandomMeal

data class AllRecipesScreenUiState(
    val meals: List<RandomMeal> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
