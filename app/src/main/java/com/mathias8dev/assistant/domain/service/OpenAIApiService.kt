package com.mathias8dev.assistant.domain.service

import com.mathias8dev.assistant.domain.model.ChatCompletionRequest
import com.mathias8dev.assistant.domain.model.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIApiService {

    @POST("chat/completions")
    suspend fun getCompletion(@Body requestBody: ChatCompletionRequest): ChatCompletionResponse
}