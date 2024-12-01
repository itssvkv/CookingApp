package com.example.cookingapp.navigation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.cookingapp.model.RandomMeal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedViewModelNavigationGraph @Inject constructor() : ViewModel() {

    private var _uiState = MutableStateFlow(SharedUiStateNavigationGraph())
    val uiState = _uiState.asStateFlow()

    fun updateUiState(meals: List<RandomMeal>, title: String) {
        _uiState.update { it.copy(meals = meals, title = title) }
    }

    fun updateSingleMealState(meal: RandomMeal, color: Color) {
        _uiState.update { it.copy(singleMeal = meal, singleMealColor = color) }
    }

}