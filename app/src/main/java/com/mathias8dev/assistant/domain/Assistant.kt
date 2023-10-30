package com.mathias8dev.assistant.domain

import androidx.compose.ui.graphics.Color
import com.mathias8dev.assistant.domain.model.ChatPrompt
import com.mathias8dev.assistant.domain.model.ChatRole
import java.util.Locale


object Assistant {

    private val personality = """
        You are a personal assistant. All of your responses are easy to understand and are
        human-like responses. Be friendly, kind and funny. 
        Your name is Assistant and your role is to assist people.
    """.trimIndent()

    val defaultPrompt: ChatPrompt
        get() {
            val currentLanguage = Locale.getDefault().displayLanguage
            return ChatPrompt(
                role = ChatRole.SYSTEM,
                content = personality +
                "By default, if the user does not write in another language, answer him in $currentLanguage"
            )
        }
}