package com.example.cookingapp.data.local.room.repository

import com.example.cookingapp.data.local.room.LocalDatabase
import com.example.cookingapp.data.local.room.dao.MyRecipesDao
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val dao: MyRecipesDao,
    private val db: LocalDatabase
) : RoomRepository {
    override suspend fun insertRecipe(recipe: SingleMealLocal) {
        dao.insertRecipe(recipe = recipe)
    }

    override suspend fun getAllMeals(): Flow<Resource<List<SingleMealLocal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipes = dao.getAllMeals()
                emit(Resource.Success(recipes))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message.toString()))

            }


        }
    }

}