package com.example.cookingapp.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote
import com.example.cookingapp.utils.Common.fromFavToSingle
import com.example.cookingapp.utils.Common.mapRecipe
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
        Log.d(TAG, "uiState: ${_uiState.value.meals}")
        getAllCategories()
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
                            searchResult = it.meals.map { meal ->
                                if (_uiState.value.favMeals.any { singleMeal -> singleMeal.idMeal == meal.idMeal }) {
                                    meal.copy(isFavorite = true)
                                } else {
                                    meal
                                }
                            },
                            categoryMeals = it.meals.map { meal ->
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
                        Log.d("home", "onSearchImeActionClicked: $meals")
                        _uiState.update { it.copy(searchResult = meals, isSearchLoading = false) }
                    },
                    onFailure = {
                        _uiState.update {
                            it.copy(
                                isSearchLoading = false,
                                searchError = "No meals found"
                            )
                        }
                    }
                )
        }
    }

    fun onSearchFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            it.copy(
                searchResult = it.searchResult?.mapIndexed { i, meal ->
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
        uiState.value.searchResult?.let {
            if (it[index].isFavorite) {
                viewModelScope.launch(Dispatchers.IO) {
                    roomRepository.insertRecipeToFavorite(
                        toFavoriteMealLocal(
                            it[index]
                        )
                    )
                }

            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    it[index].idMeal?.let { id ->
                        roomRepository.deleteRecipeFromFavorite(
                            id
                        )
                    }
                }
            }
        }

    }
    fun onCategoryFavIconClicked(isFavIconClicked: Boolean, index: Int) {
        _uiState.update {
            it.copy(
                categoryMeals = it.categoryMeals.mapIndexed { i, meal ->
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
        uiState.value.categoryMeals.let {
            if (it[index].isFavorite) {
                viewModelScope.launch(Dispatchers.IO) {
                    roomRepository.insertRecipeToFavorite(
                        toFavoriteMealLocal(
                            it[index]
                        )
                    )
                }

            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    it[index].idMeal?.let { id ->
                        roomRepository.deleteRecipeFromFavorite(
                            id
                        )
                    }
                }
            }
        }
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

    fun onOneCategoryClicked(category: String, index: Int) {
        _uiState.update {
            it.copy(
                isOneCategoryClick = !uiState.value.isOneCategoryClick,
                categoryIndex = index,
                categoryMeals = emptyList()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            getAllMealsWithMainCategory(category = category)
        }
    }

    private suspend fun getAllMealsWithMainCategory(category: String) {
        if (category.isNotEmpty() && _uiState.value.isOneCategoryClick) {
            networkRepository.getAllMealsWithMainCategory(category = category.trim())
                .onResponse(
                    onLoading = {
                        _uiState.update {
                            it.copy(isCategoryLoading = true)
                        }
                    },
                    onSuccess = { meals ->
                        if (meals != null) {
                            meals.forEach {
                                viewModelScope.launch(Dispatchers.IO) {
                                    networkRepository.getMealInfoById(id = it.idMeal.toString())
                                        .onResponse(
                                            onLoading = {
                                                _uiState.update {
                                                    it.copy(isCategoryLoading = true)
                                                }
                                            },
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
                                                    _uiState.update {
                                                        it.copy(
                                                            categoryMeals = it.categoryMeals + finalMeal,
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
                            _uiState.update {
                                it.copy(isCategoryLoading = false)
                            }

                        } else {
                            _uiState.update {
                                it.copy(
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

    fun getRandomMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertRecipeToCache(onLoading = {
                _uiState.update {
                    it.copy(
                        isLoadingMoreMeals = true
                    )
                }
                Log.d(TAG, "getRandomMeals: Loadinnnggg")
            }, onFailure = {
                _uiState.update { it.copy(isLoadingMoreMeals = false) }
                Log.d(TAG, "getRandomMeals: Failed")
            },
                onSuccess = {
                    _uiState.update { it.copy(isLoadingMoreMeals = false) }
                    Log.d(TAG, "getRandomMeals: Suuuuus")
                    getAllRandomMealsFromRoom()
                })
        }
    }

    fun getAllRandomMealsFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllMealsFromCache().onResponse(
                onLoading = {
                    _uiState.update { it.copy(isLoadingMoreMeals = true) }
                },
                onFailure = { error ->
                    Log.d(TAG, "getRandomMeals: $error")
                    _uiState.update { it.copy(isLoadingMoreMeals = false) }
                },
                onSuccess = {
                    it?.let { meals ->
                        val mealsList = meals.map { meal ->
                            mapRecipe(meal) { oneMeal ->
                                val prepTime = Random.nextInt(10, 40)
                                val cookTime = Random.nextInt(10, 40)
                                val totalTime = prepTime + cookTime
                                SingleMealLocal(
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
                            }
                        }
                        _uiState.update { state ->
                            state.copy(
                                meals = state.meals + (mealsList),
                                isLoadingMoreMeals = false
                            )
                        }
                    }
                }
            )
        }
    }


}