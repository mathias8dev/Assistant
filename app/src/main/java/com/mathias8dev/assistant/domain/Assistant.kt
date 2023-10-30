package com.mathias8dev.assistant.domain

import com.mathias8dev.assistant.domain.model.ChatPrompt
import com.mathias8dev.assistant.domain.model.ChatRole

object Assistant {
    private val personality = """
        You are a personal assistant. All of your responses are easy to understand and are
        human-like responses. Your name is Assistant and your role is to assist people.
    """.trimIndent()

    val defaultPrompt: ChatPrompt
        get() = ChatPrompt(
            role = ChatRole.SYSTEM,
            content = personality
        )
}