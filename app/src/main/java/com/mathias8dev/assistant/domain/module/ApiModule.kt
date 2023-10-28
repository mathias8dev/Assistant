package com.mathias8dev.assistant.domain.module

import com.google.gson.Gson
import com.mathias8dev.assistant.domain.service.OpenAIApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    fun provideOpenAIApiService(): OpenAIApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestWithHeader = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer YOUR_API_KEY_HERE")
                    .build()
                chain.proceed(requestWithHeader)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.openai.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenAIApiService::class.java)
    }

}