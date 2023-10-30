package com.mathias8dev.assistant.domain.model

import androidx.compose.runtime.Composable

sealed interface Resource <T> {
    data class Success <T>(
        val data: T
    ): Resource<T>

    data class Error<T>(
        val throwable: Throwable
    ) : Resource<T>

    class Loading<T>: Resource<T>
    class Idle<T> : Resource<T>
}

val Resource<*>.isSuccess: Boolean
    get() = this is Resource.Success<*>

val Resource<*>.isError: Boolean
    get() = this is Resource.Error

val Resource<*>.isLoading: Boolean
    get() = this is Resource.Loading

val Resource<*>.isDataEmpty: Boolean
    get() {
        if (!this.isSuccess) throw RuntimeException("The response is not a successfully response")
        val response = this as Resource.Success<*>
        return response.data == Unit ||
                response.data == Unit ||
                (response.data is Collection<*> && response.data.isEmpty())
    }



@Composable
fun <T> Resource<T>.OnSuccessComposable(
    content: @Composable (data: T)->Unit
) {
    if (this.isSuccess && ! this.isDataEmpty) {
        content((this as Resource.Success<T>).data)
    }
}

@Composable
fun Resource<*>.OnErrorComposable(
    content: @Composable (data: Throwable)->Unit
) {
    if (this.isError) {
        content((this as Resource.Error).throwable)
    }
}

@Composable
fun Resource<*>.OnLoadingComposable(
    content: @Composable ()->Unit
) {
    if (this.isLoading) {
        content()
    }
}