package com.example.appentretenimiento.data.model

import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.utils.Mensajes
import retrofit2.Response

abstract class BaseApiResponse {


    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        transform: (R) -> T
    ): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    //    En caso de no querer mapear, se que si no se usa hay que borrarla pero dijiste que no lo hiciesemos :)
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(Mensajes.ERROR_CONSULTA_INTERNET + errorMessage)


}