package com.example.cookingapp.presentation.screen.login

data class LoginScreenUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val isError: Boolean = false,
    val errorType: String? = null
)
