package com.example.cookingapp.navigation

import androidx.compose.ui.graphics.Color
import com.example.cookingapp.model.RandomMeal

data class SharedUiStateNavigationGraph(
    val meals: List<RandomMeal> = emptyList(),
    val title: String = "",
    val singleMeal: RandomMeal? = null,
    val singleMealColor: Color? = null
)
