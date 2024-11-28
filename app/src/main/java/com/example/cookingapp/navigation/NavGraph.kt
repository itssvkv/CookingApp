package com.example.cookingapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.presentation.screen.library.LibraryScreen
import com.example.cookingapp.presentation.screen.login.LoginScreen
import com.example.cookingapp.presentation.screen.onboarding.OnBoardingScreen
import com.example.cookingapp.presentation.screen.signup.SignupScreen
import com.example.cookingapp.presentation.screen.splash.SplashScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    startDestination: Screen = Screen.SplashScreen
) {

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable<Screen.SplashScreen> {
            SplashScreen(
                navHostController = navHostController
            )
        }
        composable<Screen.OnBoardingScreen> {
            OnBoardingScreen(navHostController = navHostController)
        }
        composable<Screen.LoginScreen> {
            LoginScreen(
                onNavigateToRegister = { navHostController.navigate(Screen.SignupScreen) },
                onNavigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.HomeScreen)
                },
                onNavigateToForgetPassword = {}
            )
        }
        composable<Screen.SignupScreen> {
            SignupScreen(
                onNavigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.HomeScreen)
                },
                onNavigateToLogin = { navHostController.navigate(Screen.LoginScreen) },
                onBackClicked = { navHostController.popBackStack() }
            )
        }
        composable<Screen.HomeScreen> {
            HomeScreen(
                isNavigateToLibrary = { navHostController.navigate(Screen.LibraryScreen) }
            )
        }

        composable<Screen.LibraryScreen> {
            LibraryScreen()
        }

    }

}