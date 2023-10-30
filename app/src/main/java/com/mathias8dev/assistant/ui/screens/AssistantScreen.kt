package com.mathias8dev.assistant.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mathias8dev.assistant.R
import com.mathias8dev.assistant.domain.model.isAssistant
import com.mathias8dev.assistant.domain.model.isError
import com.mathias8dev.assistant.domain.model.isLoading
import com.mathias8dev.assistant.domain.model.isUser
import com.mathias8dev.assistant.ui.composables.DotsTyping
import com.mathias8dev.assistant.ui.composables.ErrorDialog
import com.mathias8dev.assistant.ui.composables.LottieAnimation
import com.mathias8dev.assistant.ui.composables.Prompt
import com.mathias8dev.assistant.ui.composables.StandardDialog
import com.mathias8dev.assistant.ui.composables.insertText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun AssistantScreen(
    navigator: DestinationsNavigator,
    viewModel: AssistantViewModel = hiltViewModel()
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val chatHistories by viewModel.chatHistories.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val completionRequest by viewModel.userPromptCompletionRequest.collectAsStateWithLifecycle()
    val sideEffectLaunched = rememberSaveable { mutableStateOf(false) }
    var userPrompt by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val sentPrompt = rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }


    LaunchedEffect(chatHistories) {
        if (!sideEffectLaunched.value) {
            if (chatHistories.size > 1) {
                listState.animateScrollToItem(chatHistories.size - 1)
                sideEffectLaunched.value = true
            }
        }
    }

    ContentDetailsLayout(
        modifier = Modifier.fillMaxSize(),
        title = "Assistant",
        showBackButton = false
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
                                color = if (history.role.isAssistant) MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.3f
                                )
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .animateItemPlacement(),
                        text = history.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            if (completionRequest.isLoading) {
                item {
                    DotsTyping(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.3f
                                ), shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .animateItemPlacement(),
                        dotCount = 10
                    )
                }
            }
        }



        Prompt(
            modifier = Modifier.imePadding(),
            focusRequester = focusRequester,
            value = userPrompt,
            valuePlaceholder = "Message",
            onValueChange = {
                userPrompt = it
            },
            onSendClick = {
                userPrompt = TextFieldValue()
                sentPrompt.value = it.text
                viewModel.onGetCompletion(it.text)
            }
        )

    }

    if (completionRequest.isError) {
        ErrorDialog(
            onDismissRequest = {
                viewModel.consumeCompletionApiRequest()
                userPrompt = insertText(userPrompt, sentPrompt.value)
            }
        )
    }
}