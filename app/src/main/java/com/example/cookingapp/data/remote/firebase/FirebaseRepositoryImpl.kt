package com.example.cookingapp.data.remote.firebase

import com.example.cookingapp.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseRepository {
    override suspend fun signupUsingFirebaseAuth(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return flow {
            var isSuccess = false
            var result: FirebaseUser? = null
            emit(Resource.Loading())

            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                isSuccess = true
                result = it.user
            }.addOnFailureListener {
                isSuccess = false
            }
            if (isSuccess) {
                emit(Resource.Success(result))
            }

        }
    }


}