package com.mathias8dev.assistant.domain.model


data class ChatCompletionRequest(
    val model: String = ChatModel.GPT_35_TURBO.key,
    val messages: List<ChatMessage>
)
