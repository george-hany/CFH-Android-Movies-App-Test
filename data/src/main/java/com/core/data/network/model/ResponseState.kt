package com.core.data.network.model

sealed class ResponseState<out T> {
    data class Success<out R>(val data: R) : ResponseState<R>()

    data class Error(var code: Int?, var message: Any?) : ResponseState<Nothing>()

    data object Loading : ResponseState<Nothing>()
}
