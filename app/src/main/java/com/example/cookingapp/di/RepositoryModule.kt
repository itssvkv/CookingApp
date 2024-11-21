package com.example.cookingapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.local.datastore.DataStoreRepositoryImpl
import com.example.cookingapp.data.remote.api.MealsAPI
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.data.remote.api.NetworkRepositoryImpl
import com.example.cookingapp.data.remote.firebase.FirebaseRepository
import com.example.cookingapp.data.remote.firebase.FirebaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun providesFirebaseRepository(auth: FirebaseAuth): FirebaseRepository {
        return FirebaseRepositoryImpl(auth = auth)
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(api: MealsAPI): NetworkRepository {
        return NetworkRepositoryImpl(api = api)
    }

}