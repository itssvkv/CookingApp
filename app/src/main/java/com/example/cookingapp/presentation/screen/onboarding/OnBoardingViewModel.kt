package com.example.cookingapp.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.utils.Constants.IS_FIRST_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
     fun isOnBoardingCompleted() {
        viewModelScope.launch {
            dataStoreRepository.saveToDataStore(key = IS_FIRST_TIME, value = false)
        }
    }
}