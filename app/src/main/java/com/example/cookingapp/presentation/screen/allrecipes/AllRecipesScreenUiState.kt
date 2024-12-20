package com.example.cookingapp.presentation.screen.allrecipes

import com.example.cookingapp.model.SingleMealLocal


data class AllRecipesScreenUiState(
    val meals: List<SingleMealLocal> = emptyList(),
    val favIndexesList: List<Int?> = emptyList(),
    val favIndexesListAndValue: List<Pair<Boolean, Int?>> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isSearchLoading: Boolean = false,
    val searchResult:  List<SingleMealLocal>? = null,
    val favMeals: List<SingleMealLocal> = emptyList(),
    val isFavClicked: Boolean = false,
)
