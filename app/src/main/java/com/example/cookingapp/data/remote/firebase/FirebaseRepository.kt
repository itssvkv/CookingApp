package com.example.cookingapp.data.remote.firebase

import android.net.Uri
import com.example.cookingapp.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun signupUsingFirebaseAuth(
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<Resource<FirebaseUser?>>

    suspend fun loginUsingFirebaseAuth(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>>

    suspend fun getTheUserInfo(): Flow<Resource<FirebaseUser?>>

    suspend fun updateUserInfo(
        photo: Uri?,
        name: String,
    ): Flow<Resource<FirebaseUser?>>

    suspend fun updateUserEmail(email: String, password: String): Flow<Resource<FirebaseUser?>>
}