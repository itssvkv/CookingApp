package com.example.cookingapp.presentation.screen.generaterecipes

import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote

data class GenerateRecipesScreenUiState(
    val isIngredientLoading: Boolean = false,
    val isCategoryLoading: Boolean = false,
    val isAreaLoading: Boolean = false,
    val isIngredientError: Boolean = false,
    val isCategoryError: Boolean = false,
    val isAreaError: Boolean = false,
    val ingredientMeals: List<Meal>? = emptyList(),
    val categoryMeals: List<Meal>? = emptyList(),
    val areaMeals: List<Meal>? = emptyList(),
    val isShowBottomSheet: Boolean = false,
    val resultMeals: List<Meal> = emptyList(),
    val previousMeals: List<Meal> = emptyList(),
    val singleMealInfo: SingleMealLocal? = null,
    val ingredient: String = "",
    val category: String = "",
    val area: String = "",
)
