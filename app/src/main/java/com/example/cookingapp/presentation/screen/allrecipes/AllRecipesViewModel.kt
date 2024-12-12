package com.example.cookingapp.presentation.screen.allrecipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.SingleMealLocal
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

    private var onFinishedClick: (List<Int?>) -> Unit = { favIndexesList ->

    }

    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }


    fun onReceiveMeals(meals: List<SingleMealLocal>) {
        _uiState.update { it.copy(meals = meals) }
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
            val favIndexesListAndValue = listOf(Pair(isFavIconClicked, index))
            val favIndexesList = listOf(index)
            it.copy(meals = it.meals.mapIndexed { i, meal ->
                Log.d("Fav", "second: ${it.meals[index].isFavorite}")
                if (i == index) {
                    Log.d("Fav", "third: ${it.meals[index].isFavorite}")
                    meal.copy(
                        isFavorite = isFavIconClicked,
                        favIndexesList = it.meals[index].favIndexesList + favIndexesList
                    )

                } else {
                    Log.d("Fav", "last: ${it.meals[index].isFavorite}")
                    meal
                }
            }, favIndexesListAndValue = it.favIndexesListAndValue.mapIndexed { i, pair ->
                if (pair.second == index) {
                    Pair(isFavIconClicked, index)
                } else {
                    pair
                }
            } + favIndexesListAndValue
            )

        }
        //it.favIndexesListAndValue + favIndexesListAndValue
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


        Log.d("FavList", "Finished")
        onFinishedClick.invoke(_uiState.value.favIndexesList)
    }
}