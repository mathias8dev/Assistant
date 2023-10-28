package com.mathias8dev.assistant.domain.model

import java.util.Date



interface ChatMessage {
    val role: ChatRole
    val content: String
}