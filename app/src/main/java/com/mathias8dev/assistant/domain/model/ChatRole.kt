package com.mathias8dev.assistant.domain.model

import com.google.gson.annotations.SerializedName


enum class ChatRole(val key: String) {
    @SerializedName("assistant")
    ASSISTANT("assistant"),
    @SerializedName("user")
    USER("user"),
    @SerializedName("system")
    SYSTEM("system")
}