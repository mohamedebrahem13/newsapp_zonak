package com.example.newsapp_zonak.common

sealed class Resource<out Model> {
    data class Loading<out Model>(val loading: Boolean,val data: Model? = null) : Resource<Model>()
    data class Success<out Model>(val data: Model) : Resource<Model>()
    data class Error(val exception: Exception) : Resource<Nothing>()

    companion object {
        fun <Model> loading(
            loading: Boolean = true, data: Model? = null
        ): Resource<Model> = Loading(loading, data)

        fun <Model> success(data: Model): Resource<Model> = Success(data)
        fun <Model> error(exception: Exception): Resource<Model>  = Error(exception)
    }
}

