package com.example.cookingapp.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.cookingapp.model.Meal
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

    //generate and generate result
    fun updateGenerateResultUiState(generatedMeals: List<Meal>) {
        _uiState.update { it.copy(generatedMeals = generatedMeals) }

    }

    fun updateEditProfileScreenUiState(
        userId: String?,
        userPhoto: Uri?,
        userName: String?,
        userEmail: String?
    ) {
        _uiState.update {
            it.copy(
                userId = userId,
                userPhoto = userPhoto,
                userName = userName,
                userEmail = userEmail
            )
        }
    }

}