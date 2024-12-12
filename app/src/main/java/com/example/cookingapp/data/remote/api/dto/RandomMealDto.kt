package com.example.cookingapp.data.remote.api.dto

import com.example.cookingapp.model.SingleMealLocal
import com.google.gson.annotations.SerializedName


data class RandomMealDto(

    @SerializedName("meals") var meals: List<Meals> = listOf()

) {
    data class Meals(
        @SerializedName("idMeal") var idMeal: String? = null,
        @SerializedName("strMeal") var strMeal: String? = null,
        @SerializedName("strDrinkAlternate") var strDrinkAlternate: String? = null,
        @SerializedName("strCategory") var strCategory: String? = null,
        @SerializedName("strArea") var strArea: String? = null,
        @SerializedName("strInstructions") var strInstructions: String? = null,
        @SerializedName("strMealThumb") var strMealThumb: String? = null,
        @SerializedName("strTags") var strTags: String? = null,
        @SerializedName("strYoutube") var strYoutube: String? = null,
        @SerializedName("strIngredient1") var strIngredient1: String? = null,
        @SerializedName("strIngredient2") var strIngredient2: String? = null,
        @SerializedName("strIngredient3") var strIngredient3: String? = null,
        @SerializedName("strIngredient4") var strIngredient4: String? = null,
        @SerializedName("strIngredient5") var strIngredient5: String? = null,
        @SerializedName("strIngredient6") var strIngredient6: String? = null,
        @SerializedName("strIngredient7") var strIngredient7: String? = null,
        @SerializedName("strIngredient8") var strIngredient8: String? = null,
        @SerializedName("strIngredient9") var strIngredient9: String? = null,
        @SerializedName("strIngredient10") var strIngredient10: String? = null,
        @SerializedName("strIngredient11") var strIngredient11: String? = null,
        @SerializedName("strIngredient12") var strIngredient12: String? = null,
        @SerializedName("strIngredient13") var strIngredient13: String? = null,
        @SerializedName("strIngredient14") var strIngredient14: String? = null,
        @SerializedName("strIngredient15") var strIngredient15: String? = null,
        @SerializedName("strIngredient16") var strIngredient16: String? = null,
        @SerializedName("strIngredient17") var strIngredient17: String? = null,
        @SerializedName("strIngredient18") var strIngredient18: String? = null,
        @SerializedName("strIngredient19") var strIngredient19: String? = null,
        @SerializedName("strIngredient20") var strIngredient20: String? = null,
        @SerializedName("strMeasure1") var strMeasure1: String? = null,
        @SerializedName("strMeasure2") var strMeasure2: String? = null,
        @SerializedName("strMeasure3") var strMeasure3: String? = null,
        @SerializedName("strMeasure4") var strMeasure4: String? = null,
        @SerializedName("strMeasure5") var strMeasure5: String? = null,
        @SerializedName("strMeasure6") var strMeasure6: String? = null,
        @SerializedName("strMeasure7") var strMeasure7: String? = null,
        @SerializedName("strMeasure8") var strMeasure8: String? = null,
        @SerializedName("strMeasure9") var strMeasure9: String? = null,
        @SerializedName("strMeasure10") var strMeasure10: String? = null,
        @SerializedName("strMeasure11") var strMeasure11: String? = null,
        @SerializedName("strMeasure12") var strMeasure12: String? = null,
        @SerializedName("strMeasure13") var strMeasure13: String? = null,
        @SerializedName("strMeasure14") var strMeasure14: String? = null,
        @SerializedName("strMeasure15") var strMeasure15: String? = null,
        @SerializedName("strMeasure16") var strMeasure16: String? = null,
        @SerializedName("strMeasure17") var strMeasure17: String? = null,
        @SerializedName("strMeasure18") var strMeasure18: String? = null,
        @SerializedName("strMeasure19") var strMeasure19: String? = null,
        @SerializedName("strMeasure20") var strMeasure20: String? = null,
        @SerializedName("strSource") var strSource: String? = null,
        @SerializedName("strImageSource") var strImageSource: String? = null,
        @SerializedName("strCreativeCommonsConfirmed") var strCreativeCommonsConfirmed: String? = null,
        @SerializedName("dateModified") var dateModified: String? = null

    ) {


    }

    fun toRandomMeal(): SingleMealLocal {
        return SingleMealLocal(
            idMeal = this.meals[0].idMeal?.toInt(),
            strMeal = this.meals[0].strMeal,
            strDrinkAlternate = this.meals[0].strDrinkAlternate,
            strCategory = this.meals[0].strCategory,
            strArea = this.meals[0].strArea,
            strInstructions = this.meals[0].strInstructions,
            strMealThumb = this.meals[0].strMealThumb,
            strTags = this.meals[0].strTags,
            strYoutube = this.meals[0].strYoutube,
            strSource = this.meals[0].strSource,
            ingredient = listOf(
                this.meals[0].strIngredient1,
                this.meals[0].strIngredient2,
                this.meals[0].strIngredient3,
                this.meals[0].strIngredient4,
                this.meals[0].strIngredient5,
                this.meals[0].strIngredient6,
                this.meals[0].strIngredient7,
                this.meals[0].strIngredient8,
                this.meals[0].strIngredient9,
                this.meals[0].strIngredient10,
                this.meals[0].strIngredient11,
                this.meals[0].strIngredient12,
                this.meals[0].strIngredient13,
                this.meals[0].strIngredient14,
                this.meals[0].strIngredient15,
                this.meals[0].strIngredient16,
                this.meals[0].strIngredient17,
                this.meals[0].strIngredient18,
                this.meals[0].strIngredient19,
                this.meals[0].strIngredient20,

                ),
            measure = listOf(
                this.meals[0].strMeasure1,
                this.meals[0].strMeasure2,
                this.meals[0].strMeasure3,
                this.meals[0].strMeasure4,
                this.meals[0].strMeasure5,
                this.meals[0].strMeasure6,
                this.meals[0].strMeasure7,
                this.meals[0].strMeasure8,
                this.meals[0].strMeasure9,
                this.meals[0].strMeasure10,
                this.meals[0].strMeasure11,
                this.meals[0].strMeasure12,
                this.meals[0].strMeasure13,
                this.meals[0].strMeasure14,
                this.meals[0].strMeasure15,
                this.meals[0].strMeasure16,
                this.meals[0].strMeasure17,
                this.meals[0].strMeasure18,
                this.meals[0].strMeasure19,
                this.meals[0].strMeasure20,
            )
        )
    }

    fun toListOfRandomMeals(): List<SingleMealLocal> {
        return List(meals.size) { index ->
            SingleMealLocal(
                idMeal = this.meals[index].idMeal?.toInt(),
                strMeal = this.meals[index].strMeal,
                strDrinkAlternate = this.meals[index].strDrinkAlternate,
                strCategory = this.meals[index].strCategory,
                strArea = this.meals[index].strArea,
                strInstructions = this.meals[index].strInstructions,
                strMealThumb = this.meals[index].strMealThumb,
                strTags = this.meals[index].strTags,
                strYoutube = this.meals[index].strYoutube,
                strSource = this.meals[index].strSource,
                ingredient = listOf(
                    this.meals[index].strIngredient1,
                    this.meals[index].strIngredient2,
                    this.meals[index].strIngredient3,
                    this.meals[index].strIngredient4,
                    this.meals[index].strIngredient5,
                    this.meals[index].strIngredient6,
                    this.meals[index].strIngredient7,
                    this.meals[index].strIngredient8,
                    this.meals[index].strIngredient9,
                    this.meals[index].strIngredient10,
                    this.meals[index].strIngredient11,
                    this.meals[index].strIngredient12,
                    this.meals[index].strIngredient13,
                    this.meals[index].strIngredient14,
                    this.meals[index].strIngredient15,
                    this.meals[index].strIngredient16,
                    this.meals[index].strIngredient17,
                    this.meals[index].strIngredient18,
                    this.meals[index].strIngredient19,
                    this.meals[index].strIngredient20,

                    ),
                measure = listOf(
                    this.meals[index].strMeasure1,
                    this.meals[index].strMeasure2,
                    this.meals[index].strMeasure3,
                    this.meals[index].strMeasure4,
                    this.meals[index].strMeasure5,
                    this.meals[index].strMeasure6,
                    this.meals[index].strMeasure7,
                    this.meals[index].strMeasure8,
                    this.meals[index].strMeasure9,
                    this.meals[index].strMeasure10,
                    this.meals[index].strMeasure11,
                    this.meals[index].strMeasure12,
                    this.meals[index].strMeasure13,
                    this.meals[index].strMeasure14,
                    this.meals[index].strMeasure15,
                    this.meals[index].strMeasure16,
                    this.meals[index].strMeasure17,
                    this.meals[index].strMeasure18,
                    this.meals[index].strMeasure19,
                    this.meals[index].strMeasure20,
                )
            )
        }
    }

}