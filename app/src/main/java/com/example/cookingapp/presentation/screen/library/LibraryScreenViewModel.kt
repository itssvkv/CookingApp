package com.example.cookingapp.presentation.screen.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
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
class LibraryScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = searchQuery)
    }

    init {
        getAllMealsFormRoom()
    }

    private fun getAllMealsFormRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllMeals().onResponse(
                onLoading = {
                    Log.d(TAG, "getAllMealsFormRoom: loading")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = {meals->
                    Log.d(TAG, "getAllMealsFormRoom: success")
                    _uiState.update { it.copy(meals = meals, isLoading = false) }
                },
                onFailure = {e->
                    Log.d(TAG, "getAllMealsFormRoom: failure $e")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }





}