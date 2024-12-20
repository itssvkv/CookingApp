package com.example.cookingapp.presentation.screen.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.remote.firebase.FirebaseRepository
import com.example.cookingapp.utils.Constants.IS_LOGGED_IN
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
class SignupScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }

    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onSignupButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.signupUsingFirebaseAuth(
                email = _uiState.value.email,
                password = _uiState.value.password,
                confirmPassword = _uiState.value.confirmPassword
            ).onResponse(
                onSuccess = { response ->
                    Log.d(TAG, "onSignupButtonClicked: ${response?.email}")
                    viewModelScope.launch(Dispatchers.IO) {
                        dataStoreRepository.saveToDataStore(key = IS_LOGGED_IN, value = true)

                    }
                    _uiState.update { it.copy(isRegisterSuccessful = true, isLoading = false) }
                },
                onFailure = { msg ->
                    Log.d(TAG, "onSignupButtonClicked: $msg")
                    _uiState.update {
                        it.copy(
                            isRegisterSuccessful = false,
                            isLoading = false,
                            isError = true,
                            errorType = msg
                        )
                    }
                },
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                }
            )
        }
    }


}