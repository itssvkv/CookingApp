package com.example.cookingapp.presentation.screen.home

import android.graphics.pdf.models.ListItem
import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.RandomMeal

data class HomeScreenUiState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val categories: HomeCategoriesResponse? = null,
    val isFocused: Boolean = false,
    val meals: List<RandomMeal>? = null
)

