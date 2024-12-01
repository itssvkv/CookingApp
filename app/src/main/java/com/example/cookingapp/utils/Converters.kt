package com.example.cookingapp.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String?>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromStringToList(data: String?): List<String?> {
        return data?.split(",") ?: emptyList()
    }
}