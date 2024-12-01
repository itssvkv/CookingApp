package com.example.cookingapp.presentation.screen.allrecipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRecipesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllRecipesScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }



    fun getRandomMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getRandomMeal().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onFailure = { error ->
                    Log.d(TAG, "getRandomMeals: $error")
                    _uiState.update { it.copy(isLoading = false) }
                },
                onSuccess = { meals ->
                    _uiState.update {
                        it.copy(meals = it.meals + (meals!!), isLoading = false)
                    }
                    Log.d(TAG, "getRandomMeals: ${_uiState.value.meals.size}")
                }
            )
        }
    }
}