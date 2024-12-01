package com.example.cookingapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.cookingapp.presentation.screen.allrecipes.AllRecipesScreen
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.presentation.screen.library.LibraryScreen
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    sharedViewModelNavigationGraph: SharedViewModelNavigationGraph
) {
    navigation(
        route = BOTTOM_BAR_GRAPH_ROUTE,
        startDestination = HomeScreens.HomeScreen.route
    ) {
        composable(route = HomeScreens.HomeScreen.route) {
            HomeScreen(
                onNavigateToAllRecipesScreen = { meals, title ->
                    sharedViewModelNavigationGraph.updateUiState(meals = meals, title = title)
                    navController.navigate(HomeScreens.AllRecipesScreen.route)
                }
            )
        }

        composable(HomeScreens.LibraryScreen.route) {
            LibraryScreen()
        }
        composable(
            route = HomeScreens.AllRecipesScreen.route,


            ) {
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()
            AllRecipesScreen(meals = uiState.meals, title = uiState.title)
        }
    }
}



