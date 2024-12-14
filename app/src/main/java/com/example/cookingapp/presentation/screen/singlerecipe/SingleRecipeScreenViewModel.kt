package com.example.cookingapp.presentation.screen.singlerecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Common.fromFavToSingle
import com.example.cookingapp.utils.Common.toFavoriteMealLocal
import com.example.cookingapp.utils.onResponse
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
        getAllFromFavorite()
    }


    private fun getAllFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllFavoriteMeals().onResponse(
                onLoading = {},
                onFailure = {},
                onSuccess = { favMeals ->
                    Log.d("tez", "getAllFromFavorite: $favMeals")
                    val new = favMeals?.map { meal ->
                        fromFavToSingle(meal)
                    }
                    new?.let {
                        _uiState.value = _uiState.value.copy(favMeals = it)
                    }
                    if (_uiState.value.favMeals.any { it.idMeal == _uiState.value.mealInfo?.idMeal }) {
                        _uiState.update { it.copy(isFavClicked = true) }
                    }
                }
            )
        }
    }
    fun onFavIconClicked(isFavIconClicked: Boolean) {
        _uiState.update {
            it.copy(
                mealInfo = it.mealInfo?.copy(isFavorite = isFavIconClicked),
                isFavClicked = isFavIconClicked
            )
        }
        if (uiState.value.isFavClicked) {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepository.insertRecipeToFavorite(toFavoriteMealLocal(uiState.value.mealInfo!!))
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                uiState.value.mealInfo?.idMeal?.let { id ->
                    roomRepository.deleteRecipeFromFavorite(id)
                }
            }

        }
    }
}