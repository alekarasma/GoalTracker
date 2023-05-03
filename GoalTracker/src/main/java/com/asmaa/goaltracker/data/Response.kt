package com.asmaa.goaltracker.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */

sealed class Response<T>(val data: T? = null, val msg: String? = null) {

    class Success<T>(data: T, msg: String) : Response<T>(data, msg)
    class Error<T>(data: T? =null, msg: String?= null) : Response<T>(data, msg)
}