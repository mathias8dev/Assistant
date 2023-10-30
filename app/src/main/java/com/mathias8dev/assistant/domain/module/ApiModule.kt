package com.mathias8dev.assistant.domain.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.mathias8dev.assistant.domain.model.ChatRole
import com.mathias8dev.assistant.domain.service.OpenAIApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val apiKey = "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"

    private val chatRoleGsonAdapter = object : TypeAdapter<ChatRole>() {
        override fun write(out: JsonWriter?, value: ChatRole?) {
            out?.value(value?.key)
        }

        override fun read(`in`: JsonReader?): ChatRole {
            return `in`?.nextString()?.uppercase()?.let {
                ChatRole.valueOf(it)
            } ?: ChatRole.ASSISTANT
        }

    }



    private fun  getGson(): Gson {
       return GsonBuilder()
           .registerTypeAdapter(ChatRole::class.java, chatRoleGsonAdapter)
           .create()
    }


    @Provides
    @Singleton
    fun provideOpenAIApiService(): OpenAIApiService {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val requestWithHeader = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(requestWithHeader)
            }.addInterceptor(ApiLoggingInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.openai.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()

        return retrofit.create(OpenAIApiService::class.java)
    }

}