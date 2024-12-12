package com.example.cookingapp.presentation.screen.yourrecipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.presentation.screen.favorite.FavoriteScreenUiState
import com.example.cookingapp.utils.Common.fromFavToSingle
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourRecipesScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(YourRecipesScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllMyRecipes()
    }

    private fun getAllMyRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllMeals().onResponse(
                onLoading = {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                },
                onSuccess = { meals ->
                    meals?.let {
                        _uiState.value = _uiState.value.copy(isLoading = false, meals = it)
                    }

                }
            )

        }
    }
}