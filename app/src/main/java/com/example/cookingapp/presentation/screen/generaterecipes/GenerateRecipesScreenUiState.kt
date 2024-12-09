package com.example.cookingapp.presentation.screen.generaterecipes

import com.example.cookingapp.model.Meal

data class GenerateRecipesScreenUiState(
    val isIngredientLoading: Boolean = false,
    val isCategoryLoading: Boolean = false,
    val isAreaLoading: Boolean = false,
    val meals: List<Meal>? = emptyList(),
    val error: String? = null,
    val isShowBottomSheet : Boolean = false,
    val ingredient:String = "",
    val category:String = "",
    val area:String = "",
)
