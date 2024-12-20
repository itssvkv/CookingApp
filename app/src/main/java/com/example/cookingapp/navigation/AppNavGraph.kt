package com.example.cookingapp.navigation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cookingapp.presentation.screen.allrecipes.AllRecipesScreen
import com.example.cookingapp.presentation.screen.editprofile.EditProfileScreen
import com.example.cookingapp.presentation.screen.favorite.FavoriteScreen
import com.example.cookingapp.presentation.screen.generaterecipes.GenerateRecipesScreen
import com.example.cookingapp.presentation.screen.generateresult.GenerateResultScreen
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.presentation.screen.library.LibraryScreen
import com.example.cookingapp.presentation.screen.newrecipe.NewRecipeScreen
import com.example.cookingapp.presentation.screen.profile.ProfileScreen
import com.example.cookingapp.presentation.screen.singlerecipe.SingleRecipeScreen
import com.example.cookingapp.presentation.screen.yourrecipes.YourRecipesScreen
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
                onNavigateToGenerateRecipesScreen = {
                    navController.navigate(HomeScreens.GenerateRecipesScreen.route)
                }
            )
            Log.d("kosom", "appNavGraph: ${uiState.isFavorite} + ${uiState.index}")
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

                }
            )
            Log.d("kosom", "appNavGraph: ${uiState.isFavorite} + ${uiState.index}")
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
                    )
                }
            }
        }
        composable(route = HomeScreens.NewRecipeScreen.route) {
            NewRecipeScreen(
                onBackIconClicked = { navController.popBackStack() },
                onNavigateToLibraryScreen = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(HomeScreens.LibraryScreen.route)
                }
            )
        }
        composable(route = HomeScreens.GenerateRecipesScreen.route) {

            GenerateRecipesScreen(
                onSheetButtonClicked = { generatedMeals ->
                    sharedViewModelNavigationGraph.updateGenerateResultUiState(generatedMeals = generatedMeals)
                    navController.navigate(HomeScreens.GenerateResultScreen.route)
                },
                onNavigateToSingleRecipeScreen = { meal, color ->
                    meal?.let {
                        sharedViewModelNavigationGraph.updateSingleMealState(
                            meal = meal,
                            color = color
                        )
                        navController.navigate(HomeScreens.SingleRecipeScreen.route)
                    }

                }
            )
        }
        composable(route = HomeScreens.GenerateResultScreen.route) {
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()
            GenerateResultScreen(
                onBackIconClicked = { navController.popBackStack() },
                onButtonClicked = { meal, color ->
                    meal?.let {
                        sharedViewModelNavigationGraph.updateSingleMealState(
                            meal = meal,
                            color = color
                        )
                        navController.navigate(HomeScreens.SingleRecipeScreen.route)
                    }
                },
                meals = uiState.generatedMeals
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
                }
            )
        }
        composable(
            route = HomeScreens.ProfileScreen.route
        ) {
            ProfileScreen(
                onNavigateToProfileSettings = { userId, userPhoto, userName, userEmail ->
                    sharedViewModelNavigationGraph.updateEditProfileScreenUiState(
                        userId = userId,
                        userPhoto = userPhoto,
                        userName = userName,
                        userEmail = userEmail
                    )
                    navController.navigate(HomeScreens.EditProfileScreen.route)
                },
                onNavigateToYourRecipes = {
                    navController.navigate(HomeScreens.YourRecipesScreen.route)
                },
                onNavigateToLogOut = {
                    navController.navigate(MainScreens.LoginScreen.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
        composable(route = HomeScreens.EditProfileScreen.route) {
            val uiState by sharedViewModelNavigationGraph.uiState.collectAsState()

            EditProfileScreen(
                userId = uiState.userId,
                userPhoto = uiState.userPhoto,
                userName = uiState.userName,
                userEmail = uiState.userEmail,
                onBackIconClicked = { navController.popBackStack() },
                onUpdateUserInfoSuccess = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(HomeScreens.ProfileScreen.route)
                },
                onUpdatedEmailSuccess = {
                    navController.navigate(MainScreens.LoginScreen.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
        composable(route = HomeScreens.YourRecipesScreen.route) {
            YourRecipesScreen(
                onBackIconClicked = { navController.popBackStack() }
            )
        }
    }
}



