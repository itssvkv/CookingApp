package com.example.cookingapp.presentation.screen.newrecipe

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.data.local.room.repository.RoomRepository
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Common.toSingleMealLocal
import com.example.cookingapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject

@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewRecipeScreenUiState())
    val uiState = _uiState.asStateFlow()

//    init {
//        _uiState.update { it.copy(singleRecipe = SingleMealLocal(
//            prepTime = 0,
//            cookTime = 0
//        )) }
//    }

    fun onRecipeNameChanged(name: String) {
        _uiState.update { it.copy(strMeal = name, strInstructions = "ffffff") }
    }

    fun onRecipeAreaChanged(area: String) {
        _uiState.update { it.copy(strArea = area) }
    }

    fun onRecipeCategoryChanged(category: String) {
        _uiState.update { it.copy(strCategory = category) }
    }

    fun onPrepTimeChanged(time: String) {
        if (time.isNotEmpty()) {
            _uiState.update { it.copy(prepTime = time.toInt()) }

        } else {
            _uiState.update { it.copy(prepTime = 0) }
        }
        changeTotalTime()
    }

    fun onCookTimeChanged(time: String) {
        if (time.isNotEmpty()) {
            _uiState.update { it.copy(cookTime = time.toInt()) }
        } else {
            _uiState.update { it.copy(cookTime = 0) }
        }
        changeTotalTime()
    }

    private fun changeTotalTime() {
        _uiState.update { it.copy(totalTime = uiState.value.prepTime + uiState.value.cookTime) }
    }

    fun onPrepTimePlusCounter() {
        _uiState.update {
            it.copy(prepTime = uiState.value.prepTime + 1)
        }
    }

    fun onPrepTimeMinusCounter() {
        if (_uiState.value.prepTime > 0) {
            _uiState.update {
                it.copy(prepTime = uiState.value.prepTime - 1)
            }
        } else {
            _uiState.update { it.copy(prepTime = 0) }
        }
    }

    fun onCookTimePlusCounter() {
        _uiState.update {
            it.copy(cookTime = uiState.value.cookTime + 1)
        }
    }

    fun onCookTimeMinusCounter() {
        if (_uiState.value.cookTime > 0) {
            _uiState.update {
                it.copy(cookTime = uiState.value.cookTime - 1)
            }
        } else {
            _uiState.update { it.copy(cookTime = 0) }
        }

    }

    fun saveToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertRecipe(
                recipe = toSingleMealLocal(localRecipe = uiState.value)
            )
        }
    }

    fun onRecipeLinksChanged(links: String) {
        val linksList = links.split(" ").filterList {
            this.isNotEmpty()
        }
        linksList.forEach { link ->
            if (link.contains(".jpg")) {
                _uiState.update { it.copy(strMealThumb = link.trim()) }
            } else if (link.contains("youtube")) {
                _uiState.update { it.copy(strYoutube = link.trim()) }
            } else {
                _uiState.update { it.copy(strSource = link.trim()) }
            }
        }
    }

    fun onRecipeIngredientsChanged(ingredients: String) {
        val ingredientsList = ingredients.split(" ").filterList { this.isNotEmpty() }
        _uiState.update { it.copy(ingredient = ingredientsList) }

    }

    fun onRecipeMeasuresChanged(measures: String) {
        val measuresList = measures.split(" ").filterList { this.isNotEmpty() }
        Log.d(TAG, "onRecipeMeasuresChanged: $measuresList")

        _uiState.update { it.copy(measure = measuresList) }
    }

    fun onRecipeInstructionsChanged(instructions: String) {
        _uiState.update { it.copy(strInstructions = instructions) }
    }

    fun onRecipeImageChanged(uri: Uri?) {
        val image = uri.toString()
        _uiState.update { it.copy(recipeImageFormDevice = image) }
        Log.d(TAG, "onRecipeImageChanged: ${_uiState.value.recipeImageFormDevice}")
    }

    fun onRecipeIngredientsImagesChanged(uris: List<String?>) {
        _uiState.update { it.copy(ingredientsImagesFromDevice = uris) }
        Log.d(TAG, "onRecipeImageChanged: ${_uiState.value.ingredientsImagesFromDevice}")
    }
}
