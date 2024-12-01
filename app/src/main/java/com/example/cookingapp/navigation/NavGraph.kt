package com.example.cookingapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.presentation.screen.library.LibraryScreen
import com.example.cookingapp.presentation.screen.login.LoginScreen
import com.example.cookingapp.presentation.screen.onboarding.OnBoardingScreen
import com.example.cookingapp.presentation.screen.signup.SignupScreen
import com.example.cookingapp.presentation.screen.splash.SplashScreen
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.example.cookingapp.utils.Constants.MAIN_GRAPH_ROUTE


fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = MAIN_GRAPH_ROUTE,
        startDestination = MainScreens.SplashScreen.route
    ) {
        composable(route = MainScreens.SplashScreen.route) {
            SplashScreen(
                navHostController = navController
            )
        }
        composable(route = MainScreens.OnBoardingScreen.route) {
            OnBoardingScreen(navHostController = navController)
        }
        composable(route = MainScreens.LoginScreen.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(MainScreens.SignupScreen.route) },
                onNavigateToHome = {
                    navController.popBackStack()
                    navController.navigate(route = BOTTOM_BAR_GRAPH_ROUTE)
                },
                onNavigateToForgetPassword = {}
            )
        }
        composable(MainScreens.SignupScreen.route) {
            SignupScreen(
                onNavigateToHome = {
                    navController.popBackStack()
                    navController.navigate(route = BOTTOM_BAR_GRAPH_ROUTE)
                },
                onNavigateToLogin = { navController.navigate(MainScreens.LoginScreen.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }


    }

}