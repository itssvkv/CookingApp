package com.example.cookingapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {
    @Serializable
    data object SplashScreen : Screen()
    @Serializable
    data object OnBoardingScreen: Screen()
    @Serializable
    data object LoginScreen: Screen()
    @Serializable
    data object SignupScreen: Screen()
    @Serializable
    data object HomeScreen: Screen()
    @Serializable
    data object LibraryScreen: Screen()
}