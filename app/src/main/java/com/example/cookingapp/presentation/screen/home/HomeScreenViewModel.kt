package com.example.cookingapp.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun onSearchQueryChange(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }

    }

    fun isFocusedChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isFocused = isFocused) }
    }

    init {
        getAllCategories()
        getRandomMeals()
    }


    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getAllCategories().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    Log.d(TAG, "getAllCategories: ${response!!.categories[1]}")
                    _uiState.update { it.copy(categories = response, isLoading = false) }
                },
                onFailure = { e ->
                    Log.d(TAG, "getAllCategories: $e")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    fun getRandomMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getRandomMeal().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoadingMoreMeals = true) }
                },
                onFailure = { error ->
                    Log.d(TAG, "getRandomMeals: $error")
                    _uiState.update { it.copy(isLoadingMoreMeals = false) }
                },
                onSuccess = { meals ->
                    val newMeals = meals!!.map {
                        val prepTime = Random.nextInt(10, 40)
                        val cookTime = Random.nextInt(10, 40)
                        val totalTime = prepTime + cookTime
                        SingleMealLocal(
                            idMeal = it.idMeal,
                            strMeal = it.strMeal,
                            strDrinkAlternate = it.strDrinkAlternate,
                            strCategory = it.strCategory,
                            strArea = it.strArea,
                            strInstructions = it.strInstructions,
                            strMealThumb = it.strMealThumb,
                            strTags = it.strTags,
                            strYoutube = it.strYoutube,
                            ingredient = it.ingredient,
                            strSource = it.strSource,
                            measure = it.measure,
                            prepTime = prepTime,
                            cookTime = cookTime,
                            totalTime = totalTime
                        )
                    }
                    _uiState.update {
                        it.copy(meals = it.meals + (newMeals), isLoadingMoreMeals = false)
                    }
                    Log.d(TAG, "getRandomMeals: ${newMeals[0]}")
                }
            )
        }
    }

    fun onFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            it.copy(meals = it.meals.mapIndexed { i, meal ->
                if (i == index) {
                    meal.copy(isFavorite = !isFavIconClicked)
                } else {
                    meal
                }
            })
        }
    }


}