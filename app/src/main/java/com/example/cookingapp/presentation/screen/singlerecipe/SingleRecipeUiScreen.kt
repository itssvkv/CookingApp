package com.example.cookingapp.presentation.screen.singlerecipe

import com.example.cookingapp.model.SingleMealLocal

data class SingleRecipeUiScreen(
    val mealInfo: SingleMealLocal? = null,
    val isFavClicked :Boolean = false,
    val favIndexesListAndValue: List<Pair<Boolean, Int?>> = emptyList(),
)
