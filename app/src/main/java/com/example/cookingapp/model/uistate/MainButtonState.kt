package com.example.cookingapp.model.uistate

import kotlinx.coroutines.flow.MutableStateFlow

sealed class MainButtonState() {
    data object Empty : MainButtonState()
    data object IsLoading : MainButtonState()
    data object OnSuccess : MainButtonState()
    data object OnFailure : MainButtonState()
}

data class MainButtonStateValue(
    val empty: Boolean = true,
    val isLoading: Boolean = false,
    val onSuccess: Boolean = false,
    val onFailure: Boolean = false
)
