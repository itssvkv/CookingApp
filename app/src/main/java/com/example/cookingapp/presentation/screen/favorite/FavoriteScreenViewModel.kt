package com.example.cookingapp.presentation.screen.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.utils.Common.fromFavToSingle
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllFromFavorite()

    }

    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    private fun getAllFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllFavoriteMeals().onResponse(
                onLoading = {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it)
                },
                onSuccess = { favMeals ->
                    val new = favMeals?.map { meal ->
                        fromFavToSingle(meal)
                    }
                    new?.let {
                        _uiState.value = _uiState.value.copy(isLoading = false, meals = it)
                    }

                }
            )
            _uiState.value.meals.forEach {
                Log.d("FavoriteScreen", "Test: ${it.strMeal}")
            }
        }
    }

    fun searchForMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.searchForMealInFavorite(searchQuery = _uiState.value.searchQuery.trim())
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
                        val new = meals?.map { meal ->
                            fromFavToSingle(meal)
                        }
                        _uiState.update {
                            it.copy(
                                searchResult = new,
                                isSearchLoading = false
                            )
                        }
                    }
                )
        }
    }

    fun onFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            Log.d("Fav", "first: ${it.meals[index].isFavorite}")
            it.copy(meals = it.meals.mapIndexed { i, meal ->
                Log.d("Fav", "second: ${it.meals[index].isFavorite}")
                if (i == index) {
                    Log.d("Fav", "third: ${it.meals[index].isFavorite}")
                    meal.copy(isFavorite = isFavIconClicked)
                } else {
                    Log.d("Fav", "last: ${it.meals[index].isFavorite}")
                    meal
                }
            })
        }
        Log.d("FavoriteScreen", "onFavIconClicked: $isFavIconClicked")
        Log.d(
            "FavoriteScreen",
            "onFavIconClicked: ${_uiState.value.meals[index].isFavorite} + $index"
        )
        if (!_uiState.value.meals[index].isFavorite) {
            Log.d("FavoriteScreen", "onFavIconClicked: ${_uiState.value.meals[index].idMeal}")
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value.meals[index].idMeal?.let { roomRepository.deleteRecipeFromFavorite(it) }
            }
            getAllFromFavorite()
        }
    }


}