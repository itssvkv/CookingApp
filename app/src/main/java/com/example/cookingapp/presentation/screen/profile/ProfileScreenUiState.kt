package com.example.cookingapp.presentation.screen.profile

import android.net.Uri

data class ProfileScreenUiState(
    val userId: String? = "",
    val userPhoto: Uri? = null,
    val userName: String? = "",
    val userEmail: String? = ""
)
