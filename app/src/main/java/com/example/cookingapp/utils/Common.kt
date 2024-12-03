package com.example.cookingapp.utils

import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.screen.newrecipe.NewRecipeScreenUiState


object Common {
    fun toSingleMealLocal(localRecipe: NewRecipeScreenUiState): SingleMealLocal {
        return SingleMealLocal(
            strMeal = localRecipe.strMeal,
            strDrinkAlternate = localRecipe.strDrinkAlternate,
            strCategory = localRecipe.strCategory,
            strArea = localRecipe.strArea,
            strInstructions = localRecipe.strInstructions,
            strMealThumb = localRecipe.strMealThumb,
            strTags = localRecipe.strTags,
            strYoutube = localRecipe.strYoutube,
            strSource = localRecipe.strSource,
            ingredient = localRecipe.ingredient,
            measure = localRecipe.measure,
            prepTime = localRecipe.prepTime,
            cookTime = localRecipe.cookTime,
            totalTime = localRecipe.totalTime,
            recipeImageFormDevice = localRecipe.recipeImageFormDevice,
            ingredientsImagesFromDevice = localRecipe.ingredientsImagesFromDevice

            )
    }

    fun fromListToString(list: List<String?>): String {
        var str = ""
        list.forEach {
            str += "$it "
        }
        return str
    }

    fun fromStringToList(data: String): List<String?> {
        return data.split(" ")
    }
}