package com.example.cookingapp.presentation.screen.allrecipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Common.fromFavToSingle
import com.example.cookingapp.utils.Common.toFavoriteMealLocal
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRecipesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllRecipesScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }


    fun onReceiveMeals(meals: List<SingleMealLocal>) {
        _uiState.update { it.copy(meals = meals) }
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

                    _uiState.update {
                        it.copy(
                            meals = it.meals.map { meal ->
                                if (_uiState.value.favMeals.any { singleMeal -> singleMeal.idMeal == meal.idMeal }) {
                                    meal.copy(isFavorite = true)
                                } else {
                                    meal
                                }
                            }
                        )
                    }

                }
            )
        }
    }

    fun onSearchImeActionClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.searchForMealByName(searchQuery = _uiState.value.searchQuery)
                .onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isSearchLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        _uiState.update { it.copy(searchResult = meals, isSearchLoading = false) }
                    },
                    onFailure = {
                        _uiState.update {
                            it.copy(isSearchLoading = false)
                        }
                    }
                )
        }
    }

    fun getRandomMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getRandomMeal().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onFailure = { error ->
                    Log.d(TAG, "getRandomMeals: $error")
                    _uiState.update { it.copy(isLoading = false) }
                },
                onSuccess = { meals ->
                    _uiState.update {
                        it.copy(meals = it.meals + (meals!!), isLoading = false)
                    }
                    Log.d(TAG, "getRandomMeals: ${_uiState.value.meals.size}")
                }
            )
        }
    }


    fun onFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            it.copy(
                meals = it.meals.mapIndexed { i, meal ->
                    if (i == index) {
                        meal.copy(
                            isFavorite = isFavIconClicked,
                        )

                    } else {
                        meal
                    }
                },
            )
        }
        Log.d("FavList", "onFavIconClickedAll: ${_uiState.value.favIndexesList}")
        if (_uiState.value.meals[index].isFavorite) {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepository.insertRecipeToFavorite(toFavoriteMealLocal(_uiState.value.meals[index]))
            }

        } else {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value.meals[index].idMeal?.let { roomRepository.deleteRecipeFromFavorite(it) }
            }
        }
    }
}