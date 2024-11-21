package com.example.cookingapp.model

import com.example.cookingapp.data.remote.api.dto.CategoriesDto

data class HomeCategoriesResponse(
    val categories: List<CategoriesDto.Categories>
)
