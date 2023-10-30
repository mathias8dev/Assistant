package com.mathias8dev.assistant.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID


@Parcelize
data class ChatHistory(
    val createdAt: Date = Date(),
    val id: String = "user-" + UUID.randomUUID().toString(),
    override val role: ChatRole = ChatRole.USER,
    override val content: String
): ChatMessage, Parcelable

fun ChatHistory.toChatPrompt() = ChatPrompt(role, content)