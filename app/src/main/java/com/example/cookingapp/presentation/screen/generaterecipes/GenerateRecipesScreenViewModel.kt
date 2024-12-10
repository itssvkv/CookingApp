package com.example.cookingapp.presentation.screen.generaterecipes

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
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
class GenerateRecipesScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    init {
        getALLPreviousMeals()
    }

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

    suspend fun getAllMealsWithMainIngredient(ingredient: String) {
        if (ingredient.isNotEmpty()) {
            networkRepository.getAllMealsWithMainIngredient(ingredient = ingredient.trim())
                .onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isIngredientLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        if (meals != null) {
                            _uiState.update {
                                it.copy(
                                    ingredientMeals = meals,
                                    isIngredientLoading = false,
                                    isIngredientError = false,
                                    resultMeals = (it.resultMeals + meals).toSet().toList()
                                )
                            }
                            insertPreviousMeal()
                        } else {
                            _uiState.update {
                                it.copy(
                                    isIngredientError = true,
                                    isIngredientLoading = false
                                )
                            }
                        }

                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(isIngredientLoading = false)
                        }
                    }
                )
        }
    }

    suspend fun getAllMealsWithMainCategory(category: String) {
        if (category.isNotEmpty()) {
            networkRepository.getAllMealsWithMainCategory(category = category.trim())
                .onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isCategoryLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        if (meals != null) {
                            _uiState.update {
                                it.copy(
                                    categoryMeals = meals,
                                    isCategoryLoading = false,
                                    isCategoryError = false,
                                    resultMeals = (it.resultMeals + meals).toSet().toList()
                                )
                            }
                            insertPreviousMeal()
                        } else {
                            _uiState.update {
                                it.copy(
                                    isCategoryError = true,
                                    isCategoryLoading = false
                                )
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

    suspend fun getAllMealsWithMainArea(area: String) {
        if (area.isNotEmpty()) {
            networkRepository.getAllMealsWithMainArea(area = area.trim()).onResponse(
                onLoading = {
                    _uiState.update {
                        it.copy(isAreaLoading = true)
                    }
                },
                onSuccess = { meals ->
                    Log.d("generate", "getAllMealsWithMainIngredient: $meals")
                    if (meals != null) {
                        _uiState.update {
                            it.copy(
                                areaMeals = meals,
                                isAreaLoading = false,
                                isAreaError = false,
                                resultMeals = (it.resultMeals + meals).toSet().toList()
                            )
                        }
                        insertPreviousMeal()
                    } else {
                        _uiState.update {
                            it.copy(
                                isAreaError = true,
                                isAreaLoading = false
                            )
                        }
                    }
                },
                onFailure = { error ->
                    Log.d("generate", "getAllMealsWithMainIngredient: $error")
                    _uiState.update {
                        it.copy(isAreaLoading = false)
                    }
                }
            )
        }
    }

    private fun insertPreviousMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.resultMeals.forEach {
                roomRepository.insertPreviousRecipe(recipe = it)
            }
        }
    }

    fun getALLPreviousMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllPreviousMeals().onResponse(
                onLoading = {},
                onSuccess = { meals ->
                    meals?.let {
                        _uiState.update {
                            it.copy(previousMeals = meals)
                        }
                    }
                },
                onFailure = {}
            )
        }
    }

    suspend fun onItemClicked(meal: Meal) {
        networkRepository.getMealInfoById(id = meal.idMeal.toString()).onResponse(
            onLoading = {},
            onSuccess = { oneMeal ->
                oneMeal?.let {
                    val prepTime = Random.nextInt(10, 40)
                    val cookTime = Random.nextInt(10, 40)
                    val totalTime = prepTime + cookTime
                    val finalMeal = SingleMealLocal(
                        idMeal = oneMeal.idMeal,
                        strMeal = oneMeal.strMeal,
                        strDrinkAlternate = oneMeal.strDrinkAlternate,
                        strCategory = oneMeal.strCategory,
                        strArea = oneMeal.strArea,
                        strInstructions = oneMeal.strInstructions,
                        strMealThumb = oneMeal.strMealThumb,
                        strTags = oneMeal.strTags,
                        strYoutube = oneMeal.strYoutube,
                        strSource = oneMeal.strSource,
                        ingredient = oneMeal.ingredient,
                        measure = oneMeal.measure,
                        prepTime = prepTime,
                        cookTime = cookTime,
                        totalTime = totalTime,
                        recipeImageFormDevice = oneMeal.recipeImageFormDevice,
                        ingredientsImagesFromDevice = oneMeal.ingredientsImagesFromDevice,
                        isFavorite = oneMeal.isFavorite,
                        lastUpdated = oneMeal.lastUpdated
                    )
                    _uiState.update { it.copy(singleMealInfo = finalMeal) }
                }
            },
            onFailure = {}
        )
    }


}

