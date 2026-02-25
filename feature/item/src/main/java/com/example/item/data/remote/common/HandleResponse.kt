package com.example.item.data.remote.common

import com.example.item.domain.model.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object HandleResponse {

    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow<Resource<T>> {
        emit(Resource.Loader(true))

        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Resource.Success(body))
            } else {
                emit(Resource.Error("Empty body"))
            }
        } else {
            val errorText = response.errorBody()?.string()
            emit(Resource.Error(errorText ?: "HTTP ${response.code()} ${response.message()}"))
        }

        emit(Resource.Loader(false))

    }.catch { e ->
        if (e is CancellationException) throw e

        val msg = when (e) {
            is IOException -> "No internet connection"
            is HttpException -> "HTTP ${e.code()}: ${e.message()}"
            is IllegalStateException -> e.message ?: "Illegal state"
            else -> e.message ?: "Unknown error"
        }

        emit(Resource.Error(msg))
        emit(Resource.Loader(false))
    }
}
