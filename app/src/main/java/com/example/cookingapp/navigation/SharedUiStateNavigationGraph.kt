package com.example.cookingapp.navigation

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal

data class SharedUiStateNavigationGraph(
    val meals: List<SingleMealLocal> = emptyList(),
    val title: String = "",
    val singleMeal: SingleMealLocal? = null,
    val singleMealColor: Color? = null,
    val singleMealIndex: Int = 0,
    val isFavorite: Boolean = false,
    val favIndexesList: List<Int?> = emptyList(),
    val favIndexesListFromSingleRecipe: List<Int?> = emptyList(),
    val index: Int = 0,
    val favIndexesListAndValue: List<Pair<Boolean, Int?>> = emptyList(),


    //generate and generate result
    val generatedMeals: List<Meal> = emptyList(),

    //profile and edit profile
    val userId: String? = "",
    val userPhoto: Uri? = null,
    val userName: String? = "",
    val userEmail: String? = ""
)
