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

    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()

    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split = stringList.replace("[", "").replace("]", "").replace(" ", "").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (_: Exception) {

            }
        }
        return result
    }
}