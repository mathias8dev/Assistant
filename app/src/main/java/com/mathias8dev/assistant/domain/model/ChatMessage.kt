package com.mathias8dev.assistant.domain.model


interface ChatMessage {
    val role: ChatRole
    val content: String
}