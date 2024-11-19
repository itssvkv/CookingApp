package com.example.cookingapp.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend fun <T> Flow<Resource<T>>.onResponse(
    onSuccess: (T?) -> Unit,
    onFailure: (String?) -> Unit,
    onLoading: () -> Unit
) {
    this.collectLatest {
        when (it) {
            is Resource.Success -> onSuccess(it.data)
            is Resource.Failure -> onFailure(it.msg)
            is Resource.Loading -> onLoading()
        }
    }
}