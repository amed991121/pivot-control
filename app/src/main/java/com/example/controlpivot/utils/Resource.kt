package com.example.controlpivot.utils

sealed class Resource<T> {

    class Success<T>(val data: T): Resource<T>()

    class Error<T>(val message: Message): Resource<T>()
}
