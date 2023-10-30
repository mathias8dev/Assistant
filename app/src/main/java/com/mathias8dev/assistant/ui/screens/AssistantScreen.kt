package com.mathias8dev.assistant.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mathias8dev.assistant.domain.model.isAssistant
import com.mathias8dev.assistant.domain.model.isLoading
import com.mathias8dev.assistant.domain.model.isUser
import com.mathias8dev.assistant.ui.composables.Prompt
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun AssistantScreen(
    navigator: DestinationsNavigator,
    viewModel: AssistantViewModel = hiltViewModel()
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val chatHistories by viewModel.chatHistories.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val completionRequest by viewModel.userPromptCompletionRequest.collectAsStateWithLifecycle()
    val userPrompt = rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(chatHistories) {
        if (chatHistories.size > 1) listState.animateScrollToItem(chatHistories.size - 1)
    }

    ContentDetailsLayout(
        modifier = Modifier.fillMaxSize(),
        title = "Assistant",
        onBackClick = {
            navigator.popBackStack()
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
                .weight(1f)
                .imeNestedScroll(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            itemsIndexed(items = chatHistories, key = { _, item -> item.id }) { _, history ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (history.role.isUser)
                        Arrangement.End else Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier
                            .widthIn(max = Dp(screenWidthDp * 0.8f))
                            .background(
                                color = if (history.role.isAssistant) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .animateItemPlacement(),
                        text = history.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                if (completionRequest.isLoading) {

                }
            }
        }



        Prompt(
            modifier = Modifier.imePadding(),
            value = userPrompt.value,
            valuePlaceholder = "",
            onValueChange = {
                userPrompt.value = it
            },
            onSendClick = {
                userPrompt.value = ""
                viewModel.onGetCompletion(it)
            }
        )

    }
}