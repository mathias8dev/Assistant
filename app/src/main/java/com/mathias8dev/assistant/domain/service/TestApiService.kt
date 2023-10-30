package com.mathias8dev.assistant.domain.service

import retrofit2.http.GET
import retrofit2.http.POST

interface TestApiService {

    @POST("chat/completion")
    suspend fun hello(): Any
}