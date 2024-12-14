package com.example.cookingapp.data.remote.firebase

import android.net.Uri
import android.util.Log
import com.example.cookingapp.utils.Resource
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                                authResult.user?.sendEmailVerification()
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
                            val emailVerified = authResult.user?.isEmailVerified
                            if (emailVerified == true) {
                                continuation.resume(authResult.user) // Resume with the user
                            } else {
                                continuation.resumeWithException(Exception("Email not verified"))
                            }

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

    override suspend fun getTheUserInfo(): Flow<Resource<FirebaseUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val user = auth.currentUser
                emit(Resource.Success(user))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateUserInfo(
        photo: Uri?,
        name: String,
    ): Flow<Resource<FirebaseUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val user = auth.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                    photo?.let {
                        photoUri = it
                    }
                }

                val firebaseUser = suspendCoroutine { continuation ->
                    user?.updateProfile(profileUpdates)?.addOnSuccessListener {
                        continuation.resume(user)
                    }
                }
                emit(Resource.Success(firebaseUser))


            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message ?: "Unknown error"))
                Log.d("profile", "updateUserInfo: ${e.message}")
            }
        }

    }

    override suspend fun updateUserEmail(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val user = auth.currentUser
                val userCredential = EmailAuthProvider.getCredential(user?.email!!, password)
//                val url = "https://cooking-app-2e685.firebaseapp.com/__/auth/action?mode=action&oobCode=code"
//                val actionCodeSettings = ActionCodeSettings.newBuilder()
//                    .setUrl(url)
//                    .setAndroidPackageName("com.example.android", false, null)
//                    .build()
                val firebaseUser = suspendCoroutine { continuation ->
                    user.reauthenticate(userCredential).addOnSuccessListener {
                        user.verifyBeforeUpdateEmail(email)
                            .addOnSuccessListener {
                                continuation.resume(user)
                            }
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
                }
                emit(Resource.Success(firebaseUser))
            } catch (e: Exception) {
                emit(Resource.Failure(msg = e.message ?: "Unknown error"))
            }
        }
    }
}
