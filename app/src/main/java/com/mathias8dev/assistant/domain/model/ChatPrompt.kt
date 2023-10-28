package com.mathias8dev.assistant.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
data class ChatPrompt(
    override val role: ChatRole = ChatRole.USER,
    override val content: String
): ChatMessage, Parcelable