package com.example.cookingapp.data.remote.api

import android.util.Log
import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.Meal
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

    override suspend fun getAllMealsWithMainIngredient(ingredient: String): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllMealsWithMainIngredient(ingredient = ingredient)
                val list = response.meals.map {
                    Meal(
                        strMeal = it.strMeal,
                        strMealThumb = it.strMealThumb,
                        idMeal = it.idMeal?.toInt()
                    )
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

    override suspend fun getAllMealsWithMainCategory(category: String): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllMealsWithCategory(category = category)
                val list = response.meals.map {
                    Meal(
                        strMeal = it.strMeal,
                        strMealThumb = it.strMealThumb,
                        idMeal = it.idMeal?.toInt()
                    )
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

    override suspend fun getAllMealsWithMainArea(area: String): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllMealsWithArea(area = area)
                val list = response.meals.map {
                    Meal(
                        strMeal = it.strMeal,
                        strMealThumb = it.strMealThumb,
                        idMeal = it.idMeal?.toInt()
                    )
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

    override suspend fun getMealInfoById(id: String): Flow<Resource<SingleMealLocal>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getMealInfoById(id = id)
                emit(Resource.Success(data = response.toRandomMeal()))
            } catch (e: HttpException) {
                emit(Resource.Failure(msg = e.message!!))
            } catch (e: IOException) {
                emit(Resource.Failure(msg = "Can't reach server, check your internet connection"))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = "Unknown error happened, try again later"))
            }
        }
    }

    override suspend fun searchForMealByName(searchQuery: String): Flow<Resource<List<SingleMealLocal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.searchForMealByName(searchQuery = searchQuery)
                emit(Resource.Success(response.toListOfRandomMeals()))
                Log.d("home", "searchForMealByName: $response")
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




