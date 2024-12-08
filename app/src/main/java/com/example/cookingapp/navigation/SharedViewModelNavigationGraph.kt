package com.example.cookingapp.navigation

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.cookingapp.model.SingleMealLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedViewModelNavigationGraph @Inject constructor() : ViewModel() {

    private var _uiState = MutableStateFlow(SharedUiStateNavigationGraph())
    val uiState = _uiState.asStateFlow()

    fun updateUiState(meals: List<SingleMealLocal>, title: String) {
        _uiState.update { it.copy(meals = meals, title = title) }
    }

    fun updateSingleMealStateFromHome(meal: SingleMealLocal, color: Color, index: Int) {
        _uiState.update {
            it.copy(
                singleMeal = meal,
                singleMealColor = color,
                singleMealIndex = index
            )
        }
    }

    fun updateSingleMealState(meal: SingleMealLocal, color: Color) {
        _uiState.update { it.copy(singleMeal = meal, singleMealColor = color) }
    }

    fun updateFavIndexesListAndValue(favIndexesListAndValue: List<Pair<Boolean, Int?>>) {
        _uiState.update {
            it.copy(
                favIndexesListAndValue = favIndexesListAndValue.toSet().toList()
            )
        }
    }

    fun updateFavIndexesListAndValueFromSingleRecipe(favIndexesListAndValue: List<Pair<Boolean, Int?>>) {
        _uiState.update {
            it.copy(
                favIndexesListAndValue = it.favIndexesListAndValue + favIndexesListAndValue.toSet()
                    .toList()
            )
        }
    }

    fun onFavIconClicked(
        isFavIconClicked: Boolean,
        index: Int,
    ) {
        _uiState.update {
            it.copy(meals = it.meals.mapIndexed { i, meal ->
                if (i == index) {
                    testFavIconClicked(
                        isFavIconClicked = isFavIconClicked,
                        index = index,
                    )
                    meal.copy(
                        isFavorite = isFavIconClicked,
                    )
                } else {
                    meal
                }
            })
        }
        checkTheStateOfFavIconList(isFavIconClicked)

        Log.d("FavList", "onFavIconClickedShare: ${_uiState.value.favIndexesList}")
    }

    fun onFavIconClickedInSingleRecipeScreen(
        isFavIconClicked: Boolean,
        index: Int,
    ) {
        val favIndexesList = listOf(index)
        _uiState.update {
            it.copy(meals = it.meals.mapIndexed { i, meal ->
                if (i == index) {
                    testFavIconClicked(
                        isFavIconClicked = isFavIconClicked,
                        index = index,
                    )
                    meal.copy(
                        isFavorite = isFavIconClicked,
                    )
                } else {
                    meal
                }
            }, favIndexesListFromSingleRecipe = it.favIndexesListFromSingleRecipe + favIndexesList)
        }
        checkTheStateOfFavIconList(isFavIconClicked)
        Log.d("FavList", "onFavIconClickedShare: ${_uiState.value.favIndexesList}")
    }

    private fun checkTheStateOfFavIconList(isClicked: Boolean) {
        if (isClicked) {
            _uiState.update { it.copy(favIndexesList = it.favIndexesList + it.favIndexesListFromSingleRecipe) }
        } else {
            val list = _uiState.value.favIndexesList + _uiState.value.favIndexesListFromSingleRecipe
            list.toSet()
            Log.d("FavList", "checkTheStateOfFavIconList: $list")
            _uiState.update { it.copy(favIndexesList = list.toList()) }
        }
    }

    fun updateFavIndexesList(favIndexesList: List<Int?>) {
        _uiState.update { it.copy(favIndexesList = favIndexesList) }

    }

    private fun testFavIconClicked(
        isFavIconClicked: Boolean,
        index: Int,
    ) {
        _uiState.update {
            it.copy(
                isFavorite = isFavIconClicked,
                index = index,
            )
        }
    }

}