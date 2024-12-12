package com.example.cookingapp.presentation.screen.favorite

import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal

data class FavoriteScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val meals: List<SingleMealLocal> = emptyList(),
    val searchQuery: String = "",
    val isSearchLoading: Boolean = false,
    val searchResult:  List<SingleMealLocal>? = null
)
