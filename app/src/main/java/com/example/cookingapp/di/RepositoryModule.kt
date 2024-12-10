package com.example.cookingapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.local.datastore.DataStoreRepositoryImpl
import com.example.cookingapp.data.local.room.LocalDatabase
import com.example.cookingapp.data.local.room.dao.AllRecipesDao
import com.example.cookingapp.data.local.room.dao.FavoriteDao
import com.example.cookingapp.data.local.room.dao.MyRecipesDao
import com.example.cookingapp.data.local.room.dao.PreviousMealsDao
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.local.room.repository.RoomRepositoryImpl
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

    @Provides
    @Singleton
    fun provideDatabaseRepository(
        myRecipes: MyRecipesDao,
        favoriteDao: FavoriteDao,
        allRecipesDao: AllRecipesDao,
        previousMealsDao: PreviousMealsDao,
        networkRepository: NetworkRepository,
        db: LocalDatabase
    ): RoomRepository {
        return RoomRepositoryImpl(
            dao = myRecipes,
            favoriteDao = favoriteDao,
            allRecipesDao = allRecipesDao,
            networkRepository = networkRepository,
            previousMealsDao = previousMealsDao,
            db = db
        )
    }

}