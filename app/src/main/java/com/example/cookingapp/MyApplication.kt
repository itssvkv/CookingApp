package com.example.cookingapp

import android.app.Application
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.utils.Common
import com.example.cookingapp.utils.Common.IS_FIRST_TIME
import com.example.cookingapp.utils.Common.IS_LOGGED_IN
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var dataStoreRepository: DataStoreRepository
    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        job = CoroutineScope(Dispatchers.IO).launch {
            val isLoggedIn = dataStoreRepository.getFromDataStore(key = IS_LOGGED_IN) ?: false
            val isFirstTime = dataStoreRepository.getFromDataStore(key = IS_FIRST_TIME) ?: true

            Common.isLoggedIn = isLoggedIn
            Common.isFirstTime = isFirstTime
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        job?.cancel()
    }

    override fun onTerminate() {
        super.onTerminate()
        job?.cancel()
    }
}