package com.example.cookingapp.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.remote.firebase.FirebaseRepository
import com.example.cookingapp.model.uistate.MainButtonStateValue
import com.example.cookingapp.utils.Constants.IS_LOGGED_IN
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.truncate

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onLoginButtonClicked(

    ) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.loginUsingFirebaseAuth(
                email = _uiState.value.email,
                password = _uiState.value.password

            ).onResponse(
                onSuccess = { response ->
                    Log.d(TAG, "onSignupButtonClicked: ${response?.email}")
                    viewModelScope.launch(Dispatchers.IO) {
                        dataStoreRepository.saveToDataStore(key = IS_LOGGED_IN, value = true)

                    }
                    _uiState.update { it.copy(isLoginSuccessful = true, isLoading = false) }
                },
                onFailure = { msg ->
                    Log.d(TAG, "onSignupButtonClicked: $msg")
                    _uiState.update {
                        it.copy(
                            isLoginSuccessful = false,
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