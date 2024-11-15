package com.example.cookingapp.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Common {
    const val TAG = "itssvkv"
    val IS_FIRST_TIME = booleanPreferencesKey("IS_FIRST_TIME")
    val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    const val APP_NAME = "CookingApp"



    var isFirstTime = true
    var isLoggedIn = false
}