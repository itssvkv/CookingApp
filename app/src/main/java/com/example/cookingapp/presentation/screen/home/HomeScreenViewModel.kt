package com.example.cookingapp.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.data.remote.api.dto.CategoriesDto
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
class HomeScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun onSearchQueryChange(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }

    }

    fun isFocusedChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isFocused = isFocused) }
    }

    init {
        getAllCategories()
        getRandomMeals()
    }


    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getAllCategories().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    Log.d(TAG, "getAllCategories: ${response!!.categories[1]}")
                    _uiState.update { it.copy(categories = response, isLoading = false) }
                },
                onFailure = { e ->
                    Log.d(TAG, "getAllCategories: $e")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    private fun getRandomMeals() {
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
                        it.copy(meals = meals, isLoading = false)

                    }
                    Log.d(TAG, "getRandomMeals: ${meals?.get(0)}")
                }
            )
        }
    }

}