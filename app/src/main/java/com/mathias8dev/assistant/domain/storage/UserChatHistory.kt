package com.mathias8dev.assistant.domain.storage

import android.os.Parcelable
import com.mathias8dev.assistant.domain.model.ChatHistory
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserChatHistory(
    val history: List<ChatHistory> = emptyList()
): Parcelable