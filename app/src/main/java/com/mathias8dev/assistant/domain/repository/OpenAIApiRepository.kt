package com.mathias8dev.assistant.domain.repository

import com.mathias8dev.assistant.domain.model.ChatCompletionRequest
import com.mathias8dev.assistant.domain.model.ChatCompletionResponse
import com.mathias8dev.assistant.domain.model.ChatMessage
import com.mathias8dev.assistant.domain.model.ChatModel
import com.mathias8dev.assistant.domain.service.OpenAIApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


data class OpenAIApiRepository(
    private val openAIApiService: OpenAIApiService
) {

    fun getCompletions(
        model: ChatModel,
        history: List<ChatMessage>
    ): Flow<ChatCompletionResponse> {
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