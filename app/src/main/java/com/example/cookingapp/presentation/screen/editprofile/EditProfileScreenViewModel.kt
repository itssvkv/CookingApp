package com.example.cookingapp.presentation.screen.editprofile

import android.net.Uri
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
import kotlin.math.log

@HiltViewModel
class EditProfileScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun onNameChanged(name: String) {
        _uiState.update { it.copy(userName = name) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(updatedEmail = email) }

    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }

    }

    fun onPhotoChanged(photoUri: Uri) {
        _uiState.update { it.copy(userPhoto = photoUri) }
    }

    fun onChangeClicked() {
        _uiState.update { it.copy(isChangeClicked = !it.isChangeClicked) }
    }

    fun onReceiveUserData(userId: String?, userName: String?, userEmail: String?, userPhoto: Uri?) {
        _uiState.update {
            it.copy(
                userId = userId,
                userName = userName,
                userEmail = userEmail,
                userPhoto = userPhoto,
                updatedEmail = userEmail
            )
        }
    }

    private suspend fun updateUserInfo() {
        uiState.value.userName?.let { name ->
            Log.d("profile", "onSaveButtonClicked: fffffff")
            firebaseRepository.updateUserInfo(
                name = name,
                photo = uiState.value.userPhoto
            ).onResponse(
                onLoading = { _uiState.update { it.copy(isLoading = true) } },
                onSuccess = { user ->
                    Log.d("profile", "onSaveButtonClicked: ${user?.email}")
                    user?.let { theUser ->
                        _uiState.update {
                            it.copy(
                                userPhoto = theUser.photoUrl,
                                userName = theUser.displayName,
                                userEmail = theUser.email,
                                userId = theUser.uid,
                                isLoading = false,
                                isUserInfoUpdatedSuccessfully = true
                            )
                        }
                    }
                },
                onFailure = { e ->
                    Log.d("profile", "onSaveButtonClicked: $e")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    private suspend fun updateUserEmail() {
        if (uiState.value.updatedEmail != uiState.value.userEmail) {
            firebaseRepository.updateUserEmail(
                email = uiState.value.updatedEmail!!,
                password = uiState.value.password
            ).onResponse(
                onLoading = { _uiState.update { it.copy(isLoading = true) } },
                onSuccess = { user ->
                    Log.d("profile", "onSaveButtonClicked Success: ${user?.email}")
                    _uiState.update {
                        it.copy(
                            updatedEmail = user?.email,
                            isLoading = false,
                            isUserEmailUpdatedSuccessfully = true
                        )
                    }
                },
                onFailure = { e ->
                    Log.d("profile", "onSaveButtonClicked faluire: $e")
                    _uiState.update { it.copy(isLoading = false, error = "Password incorrect") }
                }
            )
        }
    }

    fun onSaveButtonClicked() {
        viewModelScope.launch {
            updateUserEmail()
            updateUserInfo()
        }
    }
}

