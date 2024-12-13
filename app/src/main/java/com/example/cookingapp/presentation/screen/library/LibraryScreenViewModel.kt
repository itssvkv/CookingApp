package com.example.cookingapp.presentation.screen.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
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
class LibraryScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = searchQuery)
    }

    init {
        getAllMealsFormRoom()
        getAllFromFavorite()
    }

    private fun getAllMealsFormRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllMeals().onResponse(
                onLoading = {
                    Log.d(TAG, "getAllMealsFormRoom: loading")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { meals ->
                    Log.d(TAG, "getAllMealsFormRoom: success")
                    _uiState.update { it.copy(meals = meals, isLoading = false) }
                },
                onFailure = { e ->
                    Log.d(TAG, "getAllMealsFormRoom: failure $e")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    fun searchForMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.searchForMeal(searchQuery = _uiState.value.searchQuery.trim())
                .onResponse(
                    onLoading = { _uiState.update { it.copy(isSearchLoading = true) } },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(isSearchLoading = false)
                        }
                        Log.d("library", "searchForMeal : $e")
                    },
                    onSuccess = { meals ->
                        Log.d("library", "searchForMeal sus:$meals ")
                        _uiState.update {
                            it.copy(
                                searchResult = meals,
                                isSearchLoading = false
                            )
                        }
                    }
                )
        }
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
                            meals = it.meals?.map { meal ->
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

    fun onFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            it.copy(
                meals = it.meals?.mapIndexed { i, meal ->
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
        if (_uiState.value.meals?.get(index)!!.isFavorite) {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepository.insertRecipeToFavorite(toFavoriteMealLocal(_uiState.value.meals!![index]))
            }

        } else {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value.meals!![index].idMeal?.let {
                    roomRepository.deleteRecipeFromFavorite(
                        it
                    )
                }
            }
        }
    }
}