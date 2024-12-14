package com.example.cookingapp.navigation

import com.example.cookingapp.utils.Constants.ALL_RECIPES_SCREEN
import com.example.cookingapp.utils.Constants.EDIT_PROFILE_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.FAVORITE_SCREEN
import com.example.cookingapp.utils.Constants.GENERATE_RECIPES_SCREEN
import com.example.cookingapp.utils.Constants.GENERATE_RESULT_SCREEN
import com.example.cookingapp.utils.Constants.HOME_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.LIBRARY_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.LOGIN_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.NEW_RECIPE_SCREEN
import com.example.cookingapp.utils.Constants.ONBOARDING_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.PROFILE_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.SIGNUP_SCREEN_ROUTE
import com.example.cookingapp.utils.Constants.SINGLE_RECIPE_SCREEN
import com.example.cookingapp.utils.Constants.YOUR_RECIPES_SCREEN_ROUTE
import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreens(val route: String) {

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
    data object AllRecipesScreen : HomeScreens(ALL_RECIPES_SCREEN)

    @Serializable
    data object SingleRecipeScreen : HomeScreens(SINGLE_RECIPE_SCREEN)

    @Serializable
    data object NewRecipeScreen : HomeScreens(NEW_RECIPE_SCREEN)

    @Serializable
    data object FavoriteScreen : HomeScreens(FAVORITE_SCREEN)

    @Serializable
    data object GenerateRecipesScreen : HomeScreens(GENERATE_RECIPES_SCREEN)

    @Serializable
    data object GenerateResultScreen : HomeScreens(GENERATE_RESULT_SCREEN)

    @Serializable
    data object ProfileScreen : HomeScreens(PROFILE_SCREEN_ROUTE)
    @Serializable
    data object EditProfileScreen : HomeScreens(EDIT_PROFILE_SCREEN_ROUTE)
    @Serializable
    data object YourRecipesScreen:HomeScreens(YOUR_RECIPES_SCREEN_ROUTE)

}
