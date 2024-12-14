package com.example.cookingapp.utils


sealed class Resource<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Failure<T>(data: T? = null, msg: String) : Resource<T>(data = data, msg = msg)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}

