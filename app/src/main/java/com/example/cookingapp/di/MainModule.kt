package com.example.cookingapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.local.datastore.DataStoreRepositoryImpl
import com.example.cookingapp.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    private val Context.appDataStore by preferencesDataStore(name = Constants.APP_NAME)

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext appContext: Context):
            DataStore<Preferences> {
        return appContext.appDataStore
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}