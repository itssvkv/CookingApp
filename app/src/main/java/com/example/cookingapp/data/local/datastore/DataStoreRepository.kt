package com.example.cookingapp.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.cookingapp.navigation.Screen
import com.example.cookingapp.presentation.screen.login.LoginScreenUiState
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T)
    suspend fun <T> getFromDataStore(key: Preferences.Key<T>): T?


}