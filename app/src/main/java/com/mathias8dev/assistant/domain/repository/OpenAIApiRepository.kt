package com.mathias8dev.assistant.domain.repository

import com.mathias8dev.assistant.domain.model.ChatCompletionRequest
import com.mathias8dev.assistant.domain.model.ChatCompletionResponse
import com.mathias8dev.assistant.domain.model.ChatMessage
import com.mathias8dev.assistant.domain.model.ChatModel
import com.mathias8dev.assistant.domain.model.ChatPrompt
import com.mathias8dev.assistant.domain.service.OpenAIApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject


data class OpenAIApiRepository @Inject constructor(
    private val openAIApiService: OpenAIApiService
) {

    fun getCompletions(
        model: ChatModel,
        history: List<ChatPrompt>
    ): Flow<ChatCompletionResponse> {
        Timber.d("Making completion request with model: $model and history: $history")
        return flow {
            emit(
                openAIApiService.getCompletion(
                    ChatCompletionRequest(
                        model = model.key,
                        messages = history
                    )
                )
            )
        }
    }
}