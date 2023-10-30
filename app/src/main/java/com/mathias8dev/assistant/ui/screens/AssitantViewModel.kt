package com.mathias8dev.assistant.ui.screens

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathias8dev.assistant.domain.Assistant
import com.mathias8dev.assistant.domain.model.Resource
import com.mathias8dev.assistant.domain.model.ChatHistory
import com.mathias8dev.assistant.domain.model.ChatModel
import com.mathias8dev.assistant.domain.model.ChatPrompt
import com.mathias8dev.assistant.domain.model.toChatHistory
import com.mathias8dev.assistant.domain.model.toChatPrompt
import com.mathias8dev.assistant.domain.repository.OpenAIApiRepository
import com.mathias8dev.assistant.domain.storage.UserChatHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val openAIApiRepository: OpenAIApiRepository,
    private val chatStore: DataStore<UserChatHistory>
) : ViewModel() {
    private val _chatHistories: MutableStateFlow<List<ChatHistory>> = MutableStateFlow(
        emptyList()
    )
    val chatHistories = _chatHistories.asStateFlow()
    private val _userPromptCompletionRequest = MutableStateFlow<Resource<Unit>>(Resource.Idle())
    val userPromptCompletionRequest = _userPromptCompletionRequest.asStateFlow()


    init {
        viewModelScope.launch {
            chatStore.data.collect {
                _chatHistories.emit(it.history)
                Timber.d("The current history is $it")
            }
        }
    }

    fun onGetCompletion(userPrompt: String) {
        Timber.d("Getting completion with prompt: $userPrompt")
        viewModelScope.launch {
            appendToChatHistory(
                ChatHistory(
                    content = userPrompt
                )
            )
            _userPromptCompletionRequest.emit(
                Resource.Loading()
            )
            openAIApiRepository.getCompletions(
                model = ChatModel.GPT_35_TURBO,
                history = mutableListOf<ChatPrompt>().apply {
                    add(Assistant.defaultPrompt)
                    addAll(chatHistories.value.map { it.toChatPrompt() })
                    add(ChatPrompt(content = userPrompt))
                }
            )
                .catch {
                    _userPromptCompletionRequest.emit(
                        Resource.Error(it)
                    )
                    Timber.e("An error occurred")
                    Timber.e(it)
                    removeLastFromChatHistory()
                }
                .collect {
                    Timber.d("OnGetting completion response $it")
                    _userPromptCompletionRequest.emit(
                        Resource.Success(Unit)
                    )
                    appendToChatHistory(it.toChatHistory())
            }
        }
    }

    private fun appendToChatHistory(
        history: ChatHistory
    ) {
        Timber.d("Adding $history to the history store")
        viewModelScope.launch {
            chatStore.updateData {
                UserChatHistory(
                    history = it.history.toMutableList().apply {
                        add(history)
                    }
                )
            }
        }
    }

    private fun removeLastFromChatHistory() {
        Timber.d("Removing last added to the history store")
        viewModelScope.launch {
            chatStore.updateData {
                UserChatHistory(
                    history = it.history.toMutableList().apply {
                        removeLast()
                    }
                )
            }
        }
    }

    fun consumeCompletionApiRequest() {
        viewModelScope.launch {
            _userPromptCompletionRequest.emit(
                Resource.Idle()
            )
        }
    }
}