package com.example.cookingapp.presentation.screen.generateresult

import com.example.cookingapp.model.SingleMealLocal

data class GenerateResultScreenUiState(
    val isLoading: Boolean = false,
    val singleMealInfo: SingleMealLocal? = null,
)
