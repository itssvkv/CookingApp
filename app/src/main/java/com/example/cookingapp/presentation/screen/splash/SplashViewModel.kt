package com.example.cookingapp.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.model.uistate.SplashScreenState
import com.example.cookingapp.utils.Constants.IS_FIRST_TIME
import com.example.cookingapp.utils.Constants.isFirstTime
import com.example.cookingapp.utils.Constants.isLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _splashScreenState = MutableStateFlow(SplashScreenState())
    val splashScreenState = _splashScreenState.asStateFlow()

    init {
        navigateTo()
    }

   private fun navigateTo() {
        viewModelScope.launch {
            if (!isFirstTime) {
                if (isLoggedIn) {
                    _splashScreenState.value = _splashScreenState.value.copy(
                        navigateToOnBoardingScreen = false,
                        navigateToLoginScreen = false,
                        navigateToHomeScreen = true
                    )
                } else {
                    _splashScreenState.value = _splashScreenState.value.copy(
                        navigateToOnBoardingScreen = false,
                        navigateToLoginScreen = true,
                        navigateToHomeScreen = false
                    )
                }
            } else {
                _splashScreenState.value = _splashScreenState.value.copy(
                    navigateToOnBoardingScreen = true,
                    navigateToLoginScreen = false,
                    navigateToHomeScreen = false
                )

            }
        }
    }

    fun saveFirstOpenStateToDataStore() {
        viewModelScope.launch {
            dataStoreRepository.saveToDataStore(key = IS_FIRST_TIME, value = false)
        }
    }


}