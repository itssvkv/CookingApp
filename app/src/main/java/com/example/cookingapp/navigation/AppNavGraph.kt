package com.example.cookingapp.navigation

import android.util.Log
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
import com.example.cookingapp.presentation.screen.favorite.FavoriteScreen
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.presentation.screen.library.LibraryScreen
import com.example.cookingapp.presentation.screen.newrecipe.NewRecipeScreen
import com.example.cookingapp.presentation.screen.singlerecipe.SingleRecipeScreen
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
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()
            HomeScreen(
                onNavigateToAllRecipesScreen = { meals, title ->
                    sharedViewModelNavigationGraph.updateUiState(meals = meals, title = title)
                    navController.navigate(HomeScreens.AllRecipesScreen.route)
                },
                onNavigateToSingleRecipeScreen = { singleMeal, color, index ->
                    sharedViewModelNavigationGraph.updateSingleMealStateFromHome(
                        meal = singleMeal,
                        color = color,
                        index = index
                    )
                    navController.navigate(HomeScreens.SingleRecipeScreen.route)
                },
                isFavorite = uiState.isFavorite,
                index = uiState.index
            )
        }

        composable(HomeScreens.LibraryScreen.route) {
            LibraryScreen(
                onItemClicked = { singleMeal, color ->
                    sharedViewModelNavigationGraph.updateSingleMealState(meal = singleMeal, color)
                    navController.navigate(HomeScreens.SingleRecipeScreen.route)
                },
                onNavigateToNewRecipeScreen = { navController.navigate(HomeScreens.NewRecipeScreen.route) }
            )
        }
        composable(
            route = HomeScreens.AllRecipesScreen.route,
        ) {
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()
            AllRecipesScreen(
                meals = uiState.meals, title = uiState.title,
                onBackIconClicked = {
                    navController.popBackStack()
                },
                onNavigateToSingleRecipeScreen = { singleMeal, color ->
                    sharedViewModelNavigationGraph.updateSingleMealState(
                        meal = singleMeal,
                        color = color
                    )
                    navController.navigate(HomeScreens.SingleRecipeScreen.route)

                }, onFavIconClicked = { isFavorite, index ->
                    Log.d("Fav", "appNavGraph: $isFavorite + $index")
                    sharedViewModelNavigationGraph.onFavIconClicked(isFavorite, index)
                }
            )
        }
        composable(route = HomeScreens.SingleRecipeScreen.route) {
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()
            uiState.singleMealColor?.let { color ->
                uiState.singleMeal?.let { meal ->
                    SingleRecipeScreen(
                        mealColor = color,
                        mealInfo = meal,
                        onBackIconClicked = {
                            navController.popBackStack()
                        },
                        onFavIconClicked = { isFavorite ->
                            sharedViewModelNavigationGraph.onFavIconClicked(
                                isFavorite,
                                uiState.singleMealIndex
                            )
                        }

                    )
                }
            }
        }
        composable(route = HomeScreens.NewRecipeScreen.route) {
            NewRecipeScreen(
                onBackIconClicked = { navController.popBackStack() }
            )
        }

        composable(route = HomeScreens.FavoriteScreen.route) {
            FavoriteScreen(
                onNavigateToSingleRecipeScreen = { singleMeal, color ->
                    sharedViewModelNavigationGraph.updateSingleMealState(
                        meal = singleMeal,
                        color = color
                    )
                    navController.navigate(HomeScreens.SingleRecipeScreen.route)
                }, onFavIconClicked = { isFavorite, index ->
                    Log.d("Fav", "appNavGraph: $isFavorite + $index")
                    sharedViewModelNavigationGraph.onFavIconClicked(isFavorite, index)
                })
        }
    }
}



