package com.example.cookingapp.navigation

import androidx.compose.ui.graphics.Color
import com.example.cookingapp.model.SingleMeal

data class SharedUiStateNavigationGraph(
    val meals: List<SingleMeal> = emptyList(),
    val title: String = "",
    val singleMeal: SingleMeal? = null,
    val singleMealColor: Color? = null
)
