package com.mathias8dev.assistant.domain.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ChatCompletionResponse(
    val id: String,
    @SerializedName("created")
    val createdAt: Long,
    val choices: List<ChatResponseChoice>
)

data class ChatResponseChoice(
    val message: ChatPrompt
)

fun ChatCompletionResponse.toChatHistory(): ChatHistory {
    val firstChoice = this.choices.first().message
    return ChatHistory(
        id = this.id,
        createdAt = Date(this.createdAt),
        role = firstChoice.role,
        content = firstChoice.content
    )
}