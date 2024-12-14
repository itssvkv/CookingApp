package com.example.cookingapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cookingapp.presentation.screen.login.LoginScreen
import com.example.cookingapp.presentation.screen.onboarding.OnBoardingScreen
import com.example.cookingapp.presentation.screen.signup.SignupScreen
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.MAIN_GRAPH_ROUTE


fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = MAIN_GRAPH_ROUTE,
        startDestination = if (Constants.isFirstTime) MainScreens.OnBoardingScreen.route else MainScreens.LoginScreen.route
    ) {

        composable(route = MainScreens.OnBoardingScreen.route) {
            OnBoardingScreen(navHostController = navController)
        }
        composable(route = MainScreens.LoginScreen.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(MainScreens.SignupScreen.route) },
                onNavigateToHome = {
                    navController.popBackStack()
                    navController.navigate(route = HomeScreens.HomeScreen.route)
                },
                onNavigateToForgetPassword = {}
            )
        }
        composable(MainScreens.SignupScreen.route) {
            SignupScreen(
                onNavigateToLogin = { navController.navigate(MainScreens.LoginScreen.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }


    }

}