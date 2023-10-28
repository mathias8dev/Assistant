package com.mathias8dev.assistant.domain.model

import androidx.compose.runtime.Composable
import kotlin.Exception

sealed interface ApiResponse {
    data class Success<T>(
        val data: T
    ): ApiResponse

    data class Error(
        val throwable: Throwable
    ) : ApiResponse

    data object Loading: ApiResponse
}

val ApiResponse.isSuccess: Boolean
    get() = this is ApiResponse.Success<*>

val ApiResponse.isError: Boolean
    get() = this is ApiResponse.Error

val ApiResponse.isLoading: Boolean
    get() = this is ApiResponse.Loading

val ApiResponse.isDataEmpty: Boolean
    get() {
        if (!this.isSuccess) throw RuntimeException("The response is not a successfully response")
        val response = this as ApiResponse.Success<*>
        return response.data == Unit ||
                response.data == Unit ||
                (response.data is Collection<*> && response.data.isEmpty())
    }



@Composable
fun <T> ApiResponse.OnSuccessComposable(
    content: @Composable (data: T)->Unit
) {
    if (this.isSuccess && ! this.isDataEmpty) {
        content((this as ApiResponse.Success<T>).data)
    }
}

@Composable
fun ApiResponse.OnErrorComposable(
    content: @Composable (data: Throwable)->Unit
) {
    if (this.isError) {
        content((this as ApiResponse.Error).throwable)
    }
}

@Composable
fun ApiResponse.OnLoadingComposable(
    content: @Composable ()->Unit
) {
    if (this.isLoading) {
        content()
    }
}