package com.example.cookingapp.presentation.screen.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                    _uiState.value = _uiState.value.copy(isLoading = true)
                },
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        meals = it,
                        isLoading = false
                    )
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            )
        }
    }





}