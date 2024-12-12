package com.example.cookingapp.presentation.screen.profile

import android.net.Uri
import com.example.cookingapp.model.AboutItem

data class ProfileScreenUiState(
    val userId: String? = "",
    val userPhoto: Uri? = null,
    val userName: String? = "",
    val userEmail: String? = "",
    val isShowBottomSheet: Boolean = false,
    val aboutItems: List<AboutItem> = emptyList(),
    val isOpenAlertDialog: Boolean = false
)
