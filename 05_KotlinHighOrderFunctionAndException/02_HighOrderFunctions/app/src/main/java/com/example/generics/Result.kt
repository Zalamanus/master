package com.example.generics


sealed class Result<out T,R>(
) {
    class Success<T,R>(result: T): Result<T,R>()
    class Error<T,R>(error: R): Result<T,R>()

    fun getResult(): Result<Int,String> {
        return Success(1)
    }
}