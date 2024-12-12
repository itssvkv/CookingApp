package com.example.cookingapp.presentation.screen.editprofile

import android.net.Uri

data class EditProfileScreenUiState(
    val userId: String? = "",
    val userPhoto: Uri? = null,
    val userName: String? = "",
    val userEmail: String? = "",
    val updatedEmail: String? = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isChangeClicked: Boolean = false,
    val isUserInfoUpdatedSuccessfully: Boolean = false,
    val isUserEmailUpdatedSuccessfully: Boolean = false,
    val error: String? = null
)
