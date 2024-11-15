package com.example.cookingapp.model.screenstate

data class SplashScreenState(
    val navigateToOnBoardingScreen: Boolean = true,
    val navigateToLoginScreen: Boolean = false,
    val navigateToHomeScreen: Boolean = false
)
