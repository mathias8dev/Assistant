package com.mathias8dev.assistant.domain.storage
import androidx.datastore.core.Serializer
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

class UserChatHistorySerializer: Serializer<UserChatHistory> {
    private val gson = Gson()
    override val defaultValue: UserChatHistory
        get() = UserChatHistory()

    override suspend fun readFrom(input: InputStream): UserChatHistory {
        InputStreamReader(input).use { reader ->
            return gson.fromJson(reader, UserChatHistory::class.java)
        }
    }

    override suspend fun writeTo(t: UserChatHistory, output: OutputStream) {
        OutputStreamWriter(output).use { writer ->
            gson.toJson(t, UserChatHistory::class.java, writer)
        }
    }

    companion object {
        const val filename = "user-chat-history.json"
    }
}