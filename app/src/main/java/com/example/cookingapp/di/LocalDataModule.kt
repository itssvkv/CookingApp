package com.example.cookingapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.local.datastore.DataStoreRepositoryImpl
import com.example.cookingapp.utils.Common
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    private val Context.appDataStore by preferencesDataStore(name = Common.APP_NAME)

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext appContext: Context):
            DataStore<Preferences> {
        return appContext.appDataStore
    }

    @Provides
    @Singleton
    fun providesDataStoreRepository(dataStore: DataStore<Preferences>) : DataStoreRepository {

        return DataStoreRepositoryImpl(dataStore)
    }
}