package com.example.cookingapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cookingapp.data.local.room.dao.MyRecipesDao
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Converters

@Database(entities = [SingleMealLocal::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun recipeDao(): MyRecipesDao
}