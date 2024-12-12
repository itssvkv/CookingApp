package com.example.cookingapp.presentation.screen.yourrecipes

import com.example.cookingapp.model.SingleMealLocal

data class YourRecipesScreenUiState(
    val meals: List<SingleMealLocal> = emptyList(),
    val isLoading: Boolean = false,
)
