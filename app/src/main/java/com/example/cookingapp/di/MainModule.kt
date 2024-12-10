package com.example.cookingapp.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.local.datastore.DataStoreRepositoryImpl
import com.example.cookingapp.data.local.room.LocalDatabase
import com.example.cookingapp.data.remote.api.MealsAPI
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.BASE_URL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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


    @Provides
    @Singleton
    fun provideHTTPClient(): OkHttpClient {
        val httpClientLoggingInterceptor = HttpLoggingInterceptor { msg ->
            Log.i("NetworkInterceptor", "Result : $msg")
        }
        httpClientLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().apply {
            addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                chain.proceed(newRequest.build())
            }
            addInterceptor(httpClientLoggingInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
        }.build()
    }


    @Provides
    @Singleton
    fun provideAPI(client: OkHttpClient): MealsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MealsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesMyRecipesDao(database: LocalDatabase) = database.recipeDao()

    @Provides
    @Singleton
    fun providesFavoriteDao(database: LocalDatabase) = database.favoriteDao()

    @Provides
    @Singleton
    fun providesAllRecipesDao(database: LocalDatabase) = database.allRecipesDao()

    @Provides
    @Singleton
    fun providesPreviousMealsDao(database: LocalDatabase) = database.previousMealsDao()

}