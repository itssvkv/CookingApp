package com.example.cookingapp.data.remote.firebase

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseRepository {

    override suspend fun signupUsingFirebaseAuth(
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<Resource<FirebaseUser?>> {
        return flow {
            emit(Resource.Loading())
            if (password != confirmPassword) {
                emit(Resource.Failure(msg = "Passwords do not match!"))
            } else {
                try {
                    val firebaseUser = suspendCoroutine { continuation ->
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener { authResult ->
                                continuation.resume(authResult.user) // Resume with the user
                            }
                            .addOnFailureListener { exception ->
                                continuation.resumeWithException(exception) // Resume with an exception
                            }
                    }
                    emit(Resource.Success(firebaseUser)) // Emit success state
                } catch (e: Exception) {
                    emit(Resource.Failure(msg = e.message ?: "Unknown error")) // Emit failure state
                }
            }
        }
    }

    override suspend fun loginUsingFirebaseAuth(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val firebaseUser = suspendCoroutine { continuation ->
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { authResult ->
                            continuation.resume(authResult.user)

                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
                emit(Resource.Success(firebaseUser))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message ?: "Unknown error"))
            }
        }
    }


}