package com.example.cookingapp.presentation.screen.generaterecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateRecipesScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenerateRecipesScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun updateIsShowBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(isShowBottomSheet = value)
        }

    }

    fun onIngredientValueChange(ingredient: String) {
        _uiState.update {
            it.copy(ingredient = ingredient)
        }
    }

    fun onCategoryValueChange(category: String) {
        _uiState.update {
            it.copy(category = category)
        }
    }

    fun onAreaValueChange(area: String) {
        _uiState.update {
            it.copy(area = area)
        }
    }

    fun getAllMealsWithMainIngredient(ingredient: String) {
        if (ingredient.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                networkRepository.getAllMealsWithMainIngredient(ingredient = ingredient).onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isIngredientLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        meals?.let { newMeals ->
                            _uiState.update {
                                it.copy(meals = it.meals?.plus((newMeals)), isIngredientLoading = false)
                            }
                        }
                    },
                    onFailure = {
                        _uiState.update {
                            it.copy(isIngredientLoading = false)
                        }
                    }
                )
            }
        }
    }

    fun getAllMealsWithMainCategory(category: String) {
        if (category.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                networkRepository.getAllMealsWithMainCategory(category = category).onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isCategoryLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        meals?.let { newMeals ->
                            _uiState.update {
                                it.copy(meals = it.meals?.plus((newMeals)), isCategoryLoading = false)
                            }
                        }
                    },
                    onFailure = {
                        _uiState.update {
                            it.copy(isCategoryLoading = false)
                        }
                    }
                )
            }
        }
    }

    fun getAllMealsWithMainArea(area: String) {
        if (area.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                networkRepository.getAllMealsWithMainArea(area = area).onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isAreaLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        meals?.let { newMeals ->
                            _uiState.update {
                                it.copy(meals = it.meals?.plus((newMeals)), isAreaLoading = false)
                            }
                        }
                    },
                    onFailure = {
                        _uiState.update {
                            it.copy(isAreaLoading = false)
                        }
                    }
                )
            }
        }

    }

}