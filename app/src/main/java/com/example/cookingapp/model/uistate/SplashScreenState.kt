package com.example.cookingapp.model.uistate

data class SplashScreenState(
    val navigateToOnBoardingScreen: Boolean = true,
    val navigateToLoginScreen: Boolean = false,
    val navigateToHomeScreen: Boolean = false
)
