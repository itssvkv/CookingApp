package com.example.cookingapp.data.local.room.repository

import android.util.Log
import com.example.cookingapp.data.local.room.LocalDatabase
import com.example.cookingapp.data.local.room.dao.AllRecipesDao
import com.example.cookingapp.data.local.room.dao.FavoriteDao
import com.example.cookingapp.data.local.room.dao.MyRecipesDao
import com.example.cookingapp.data.remote.api.NetworkRepository
import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.model.SingleMealRemote
import com.example.cookingapp.utils.Common.mapRecipe
import com.example.cookingapp.utils.Resource
import com.example.cookingapp.utils.onResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class RoomRepositoryImpl @Inject constructor(
    private val dao: MyRecipesDao,
    private val favoriteDao: FavoriteDao,
    private val allRecipesDao: AllRecipesDao,
    private val networkRepository: NetworkRepository,
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


    override suspend fun insertRecipeToFavorite(recipe: FavoriteMealLocal) {
        favoriteDao.insertRecipe(recipe = recipe)
    }

    override suspend fun getAllFavoriteMeals(): Flow<Resource<List<FavoriteMealLocal>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipes = favoriteDao.getAllMeals()
                emit(Resource.Success(recipes))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message.toString()))

            }
        }
    }

    override suspend fun deleteRecipeFromFavorite(idMeal: Int) {
        favoriteDao.deleteRecipe(idMeal = idMeal)
    }

    override suspend fun insertRecipeToCache(
        onLoading: () -> Unit,
        onFailure: () -> Unit,
        onSuccess: () -> Unit
    ) {
        var list = emptyList<SingleMealRemote>()
        networkRepository.getRandomMeal().onResponse(
            onLoading = onLoading,
            onFailure = { onFailure() },
            onSuccess = {
                onSuccess()
                it?.let { meals ->
                    list = meals.map { meal ->
                        mapRecipe(meal) { oneMeal ->
                            val prepTime = Random.nextInt(10, 40)
                            val cookTime = Random.nextInt(10, 40)
                            val totalTime = prepTime + cookTime
                            SingleMealRemote(
                                idMeal = oneMeal.idMeal,
                                strMeal = oneMeal.strMeal,
                                strDrinkAlternate = oneMeal.strDrinkAlternate,
                                strCategory = oneMeal.strCategory,
                                strArea = oneMeal.strArea,
                                strInstructions = oneMeal.strInstructions,
                                strMealThumb = oneMeal.strMealThumb,
                                strTags = oneMeal.strTags,
                                strYoutube = oneMeal.strYoutube,
                                strSource = oneMeal.strSource,
                                ingredient = oneMeal.ingredient,
                                measure = oneMeal.measure,
                                prepTime = prepTime,
                                cookTime = cookTime,
                                totalTime = totalTime,
                                recipeImageFormDevice = oneMeal.recipeImageFormDevice,
                                ingredientsImagesFromDevice = oneMeal.ingredientsImagesFromDevice,
                                isFavorite = oneMeal.isFavorite
                            )
                        }
                    }
                }
            }
        )
        list.forEach {
            allRecipesDao.insertRecipe(recipe = it)
        }
    }

    override suspend fun getAllMealsFromCache(): Flow<Resource<List<SingleMealRemote>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = allRecipesDao.getAllMeals()
                emit(Resource.Success(data = response))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message.toString()))
            }
        }
    }


}