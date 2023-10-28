package com.mathias8dev.assistant.domain.model

import java.util.Date

data class ChatCompletionResponse(
    val id: String,
    val createdAt: Date,
    val choices: List<ChatResponseChoice>
)

data class ChatResponseChoice(
    override val role: ChatRole,
    override val content: String
): ChatMessage