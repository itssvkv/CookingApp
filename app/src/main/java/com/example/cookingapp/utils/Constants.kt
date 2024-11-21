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

    enum class CustomTextFieldLabel {
        EMAIL,
        PASSWORD,
        SEARCH
    }
}

