package com.app.imkbapp.data.remoteDataSource

import com.app.imkbapp.model.Resource
import retrofit2.Response

abstract class BaseDataSource {

    open suspend fun  <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Resource.success(result.body())
            } else {
                Resource.error(defaultErrorMessage, null)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.error("Error ${e.message}", null)
        }
    }
}