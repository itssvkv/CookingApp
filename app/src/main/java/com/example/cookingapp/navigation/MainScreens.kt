package com.example.cookingapp.navigation

import com.example.cookingapp.utils.Constants.ALL_RECIPES_SCREEN
import com.example.cookingapp.utils.Constants.FAVORITE_SCREEN
import com.example.cookingapp.utils.Constants.HOME_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.LIBRARY_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.LOGIN_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.NEW_RECIPE_SCREEN
import com.example.cookingapp.utils.Constants.ONBOARDING_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.SIGNUP_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.SINGLE_RECIPE_SCREEN
import com.example.cookingapp.utils.Constants.SPLASH_SCREEN_ROUTE
import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreens(val route: String) {
    @Serializable
    data object SplashScreen : MainScreens(route = SPLASH_SCREEN_ROUTE)

    @Serializable
    data object OnBoardingScreen : MainScreens(route = ONBOARDING_SCREEN_ROUTE)

    @Serializable
    data object LoginScreen : MainScreens(route = LOGIN_SCREEN_ROUTE)

    @Serializable
    data object SignupScreen : MainScreens(SIGNUP_SCREEN_ROUTE)


}

@Serializable
sealed class HomeScreens(val route: String) {
    @Serializable
    data object HomeScreen : HomeScreens(HOME_SCREEN_ROUTE)

    @Serializable
    data object LibraryScreen : HomeScreens(LIBRARY_SCREEN_ROUTE)
    @Serializable
    data object AllRecipesScreen: HomeScreens(ALL_RECIPES_SCREEN)
    @Serializable
    data object SingleRecipeScreen: HomeScreens(SINGLE_RECIPE_SCREEN)
    @Serializable
    data object NewRecipeScreen: HomeScreens(NEW_RECIPE_SCREEN)
    data object FavoriteScreen : HomeScreens(FAVORITE_SCREEN)
}
