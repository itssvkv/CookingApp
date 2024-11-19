package com.example.cookingapp.data.remote.firebase

import com.example.cookingapp.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun signupUsingFirebaseAuth(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>>

}