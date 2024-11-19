package com.example.cookingapp.presentation.screen.signup

data class SignupScreenUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isRegisterSuccessful: Boolean = false,
    val isError: Boolean = false,
    val errorType: String? = null
)
