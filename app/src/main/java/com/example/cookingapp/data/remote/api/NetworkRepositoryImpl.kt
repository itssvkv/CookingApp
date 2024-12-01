package com.example.cookingapp.data.remote.api

import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: MealsAPI
) : NetworkRepository {
    override suspend fun getAllCategories(): Flow<Resource<HomeCategoriesResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllCategories()
                emit(Resource.Success(response.toHomeCategories()))
            } catch (e: HttpException) {
                emit(Resource.Failure(msg = e.message!!))
            } catch (e: IOException) {
                emit(Resource.Failure(msg = "Can't reach server, check your internet connection"))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = "Unknown error happened, try again later"))
            }
        }
    }

    override suspend fun getRandomMeal(

    ): Flow<Resource<List<SingleMealLocal>>> {
        var list: List<SingleMealLocal> = emptyList()

        return flow {
            emit(Resource.Loading())
            try {
                for (i in 0..9) {
                    val response = api.getRandomMeal()
                    list = list + response.toRandomMeal()
                }
                emit(Resource.Success(data = list))

            } catch (e: HttpException) {
                emit(Resource.Failure(msg = e.message!!))
            } catch (e: IOException) {
                emit(Resource.Failure(msg = "Can't reach server, check your internet connection"))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = "Unknown error happened, try again later"))
            }
        }
    }


}


