package com.example.cookingapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cookingapp.utils.Constants.ROOT_GRAPH_ROUTE

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startGraph: String,
    sharedViewModelNavigationGraph: SharedViewModelNavigationGraph = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startGraph,
        route = ROOT_GRAPH_ROUTE
    ) {
        mainNavGraph(navController = navController)
        appNavGraph(
            navController = navController,
            sharedViewModelNavigationGraph = sharedViewModelNavigationGraph
        )
    }
}