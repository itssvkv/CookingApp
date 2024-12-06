package com.example.cookingapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.ContextCompat
import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.screen.newrecipe.NewRecipeScreenUiState
import kotlin.reflect.KClass


object Common {

    fun <T, R> mapRecipe(input: T, mapper: (T) -> R): R {
        return mapper(input)
    }

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
            ingredientsImagesFromDevice = localRecipe.ingredientsImagesFromDevice,
            isFavorite = localRecipe.isFavorite
        )
    }



    fun toFavoriteMealLocal(localRecipe: SingleMealLocal): FavoriteMealLocal {
        return FavoriteMealLocal(
            idMeal = localRecipe.idMeal,
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
            ingredientsImagesFromDevice = localRecipe.ingredientsImagesFromDevice,
            isFavorite = localRecipe.isFavorite

        )
    }

    fun fromFavToSingle(favMeal: FavoriteMealLocal): SingleMealLocal {
        return SingleMealLocal(
            idMeal = favMeal.idMeal,
            strMeal = favMeal.strMeal,
            strDrinkAlternate = favMeal.strDrinkAlternate,
            strCategory = favMeal.strCategory,
            strArea = favMeal.strArea,
            strInstructions = favMeal.strInstructions,
            strMealThumb = favMeal.strMealThumb,
            strTags = favMeal.strTags,
            strYoutube = favMeal.strYoutube,
            strSource = favMeal.strSource,
            ingredient = favMeal.ingredient,
            measure = favMeal.measure,
            prepTime = favMeal.prepTime,
            cookTime = favMeal.cookTime,
            totalTime = favMeal.totalTime,
            recipeImageFormDevice = favMeal.recipeImageFormDevice,
            ingredientsImagesFromDevice = favMeal.ingredientsImagesFromDevice,
            isFavorite = favMeal.isFavorite
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

    fun openGalleryForMultipleImages(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>? = null
    ) {
        val intent =
            Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple images
            }
        launcher?.launch(intent) ?: context.startActivity(intent)
    }

    fun permissionChecker(
        context: Context,
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        multiImageLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGalleryForMultipleImages(context, multiImageLauncher)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}