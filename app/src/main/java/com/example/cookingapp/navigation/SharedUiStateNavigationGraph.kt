package com.example.cookingapp.navigation

import androidx.compose.ui.graphics.Color
import com.example.cookingapp.model.SingleMealLocal

data class SharedUiStateNavigationGraph(
    val meals: List<SingleMealLocal> = emptyList(),
    val title: String = "",
    val singleMeal: SingleMealLocal? = null,
    val singleMealColor: Color? = null
)
