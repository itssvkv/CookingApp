package com.example.cookingapp.presentation.screen.generateresult

import androidx.lifecycle.ViewModel
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GenerateResultScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenerateResultScreenUiState())
    val uiState = _uiState.asStateFlow()

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