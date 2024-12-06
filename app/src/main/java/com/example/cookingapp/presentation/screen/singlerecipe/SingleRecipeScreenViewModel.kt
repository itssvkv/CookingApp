package com.example.cookingapp.presentation.screen.singlerecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Common.toFavoriteMealLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleRecipeScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SingleRecipeUiScreen())
    val uiState = _uiState.asStateFlow()

    fun onReceiveMealInfo(mealInfo: SingleMealLocal) {
        _uiState.update { it.copy(mealInfo = mealInfo) }
    }


    fun onFavIconClicked(isFavIconClicked: Boolean) {
        _uiState.update {
            it.copy(mealInfo = it.mealInfo?.copy(isFavorite = isFavIconClicked))
        }
        _uiState.value.mealInfo?.let {
            if (it.isFavorite) {
                viewModelScope.launch(Dispatchers.IO) {
                    roomRepository.insertRecipeToFavorite(toFavoriteMealLocal(it))
                }

            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    it.idMeal?.let { id ->
                        roomRepository.deleteRecipeFromFavorite(id)
                    }
                }
            }
        }


    }
}