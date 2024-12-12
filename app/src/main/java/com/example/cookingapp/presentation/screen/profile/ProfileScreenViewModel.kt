package com.example.cookingapp.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.remote.firebase.FirebaseRepository
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserInfo()
        }
    }

    private suspend fun getUserInfo() {
        firebaseRepository.getTheUserInfo().onResponse(
            onLoading = {},
            onSuccess = { user ->
                user?.let { theUser ->
                    _uiState.update {
                        it.copy(
                            userPhoto = theUser.photoUrl,
                            userName = theUser.displayName,
                            userEmail = theUser.email,
                            userId = theUser.uid
                        )
                    }
                }
            },
            onFailure = {}
        )
    }
}