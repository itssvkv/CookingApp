package com.example.cookingapp.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val TAG = "itssvkv"
    val IS_FIRST_TIME = booleanPreferencesKey("IS_FIRST_TIME")
    val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    const val APP_NAME = "CookingApp"


    var isFirstTime = true
    var isLoggedIn = false

    const val BASE_URL = "https://www.themealdb.com/"
    const val COMMON_URL = "api/json/v1/1/"
    const val LIST = "list.php"
    const val ALL_CATEGORIES = COMMON_URL + "categories.php"
    const val RANDOM_MEAL = COMMON_URL + "random.php"
    const val FILTER_INGREDIENT = COMMON_URL + "filter.php"
    const val MEAL_BY_ID = COMMON_URL + "lookup.php"
    const val SEARCH_MEAL_BY_NAME = COMMON_URL + "search.php"
    const val INGREDIENT = "https://www.themealdb.com/images/ingredients/"

    val theIngredient = ""
    val INGREDIENT_CALL_URL = "$INGREDIENT$theIngredient.png"

    enum class CustomTextFieldLabel {
        EMAIL,
        PASSWORD,
        SEARCH,
        NAME
    }

    //Screens Routes
    const val SPLASH_SCREEN_ROUTE = "splash_screen_route"
    const val ONBOARDING_SCREEN_ROUTE = "onboarding_screen_route"
    const val LOGIN_SCREEN_ROUTE = "login_screen_route"
    const val SIGNUP_SCREEN_ROUTE = "signup_screen_route"
    const val HOME_SCREEN_ROUTE = "home_screen_route"
    const val LIBRARY_SCREEN_ROUTE = "library_screen"
    const val ALL_RECIPES_SCREEN = "all_recipes_screen}"
    const val SINGLE_RECIPE_SCREEN = "single_recipe_screen"
    const val NEW_RECIPE_SCREEN = "new_recipe_screen"
    const val FAVORITE_SCREEN = "favorite_screen_route"
    const val GENERATE_RECIPES_SCREEN = "generate_recipes_screen"
    const val GENERATE_RESULT_SCREEN = "generate_result_screen"
    const val PROFILE_SCREEN_ROUTE = "profile_screen_route"
    const val EDIT_PROFILE_SCREEN_ROUTE = "edit_profile_screen_route"
    const val YOUR_RECIPES_SCREEN_ROUTE = "your_recipes_screen_route"
    const val SEARCH_SCREEN_ROUTE = "search_screen_route"
    const val CREATE_TASK_SCREEN_ROUTE = "create_task_screen_route/{task_id}"
    const val ANALYTICS_SCREEN_ROUTE = "analytics_screen_route"
    const val TASK_DETAILS_SCREEN_ROUTE = "task_details_screen_route/{task_id}"

    //NavGraphs Routes
    const val MAIN_GRAPH_ROUTE = "main_graph_route"
    const val BOTTOM_BAR_GRAPH_ROUTE = "bottom_bar_graph_route"
    const val ROOT_GRAPH_ROUTE = "root_graph_route"
}

