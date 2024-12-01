package com.example.cookingapp.navigation

import com.example.cookingapp.model.RandomMeal

data class SharedUiStateNavigationGraph(
    val meals: List<RandomMeal> = emptyList(),
    val title: String = ""
)
