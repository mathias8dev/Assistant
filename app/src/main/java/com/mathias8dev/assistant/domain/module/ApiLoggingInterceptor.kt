package com.mathias8dev.assistant.domain.module

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber


object ApiLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.tag("HttpLoggingInterceptor").d("Making request to: ${request.url}")
        Timber.tag("HttpLoggingInterceptor").d("IsHttps request: ${request.isHttps}")
        Timber.tag("HttpLoggingInterceptor").d("Headers are: ${request.headers}")
        Timber.tag("HttpLoggingInterceptor").d("The method is: ${request.method}")
        Timber.tag("HttpLoggingInterceptor")
            .d("The body content type is: ${request.body?.contentType()}")
        Timber.tag("HttpLoggingInterceptor").d("The body is: ${request.body?.toString()}")

        return chain.proceed(request)
    }

}