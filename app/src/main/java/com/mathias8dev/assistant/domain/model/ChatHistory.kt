package com.mathias8dev.assistant.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID


@Parcelize
data class ChatHistory(
    val createdAt: Date,
    val id: String = "gen " + UUID.randomUUID().toString(),
    override val role: ChatRole,
    override val content: String
): ChatMessage, Parcelable