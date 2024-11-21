package com.example.cookingapp.data.remote.api.dto

import com.example.cookingapp.model.HomeCategoriesResponse
import com.google.gson.annotations.SerializedName

data class CategoriesDto(
    @SerializedName("categories") var categories: List<Categories> = listOf()
) {
    data class Categories(

        @SerializedName("idCategory") var idCategory: String? = null,
        @SerializedName("strCategory") var strCategory: String? = null,
        @SerializedName("strCategoryThumb") var strCategoryThumb: String? = null,
        @SerializedName("strCategoryDescription") var strCategoryDescription: String? = null

    )

    fun toHomeCategories(): HomeCategoriesResponse {
        return HomeCategoriesResponse(
            categories = this.categories
        )
    }
}