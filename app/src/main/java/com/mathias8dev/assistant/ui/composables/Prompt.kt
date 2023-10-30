package com.mathias8dev.assistant.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp



@Composable
fun Prompt(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    valuePlaceholder: String,
    onValueChange: (updatedValue: TextFieldValue) -> Unit,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onSendClick: (value: TextFieldValue) -> Unit
) {
    PromptLayout(
        modifier = modifier,
        enableSendButton = value.text.isNotEmpty(),
        onSendClick = {
            onSendClick(value)
        }
    ) {
        StandardTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(0.dp)
                .weight(1f).heightIn(min = 40.dp, max = 150.dp),
            value = value,
            placeholder = {
                Text(
                    text = valuePlaceholder
                )
            },
            onValueChange = onValueChange,
            contentPadding = PaddingValues(top = 8.dp, start = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}


@Composable
fun Prompt(
    modifier: Modifier = Modifier,
    value: String,
    valuePlaceholder: String,
    onValueChange: (updatedValue: String) -> Unit,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onSendClick: (sentValue: String) -> Unit
) {

    PromptLayout(
        modifier = modifier,
        enableSendButton = value.isNotEmpty(),
        onSendClick = {
            onSendClick (value)
        }
    ) {
        StandardTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(0.dp)
                .weight(1f)
                .heightIn(min = 40.dp, max = 150.dp),
            value = value,
            placeholder = {
                Text(
                    text = valuePlaceholder
                )
            },
            onValueChange = onValueChange,
            contentPadding = PaddingValues(top = 8.dp, start = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }

}


@Composable
internal fun PromptLayout(
    modifier: Modifier = Modifier,
    enableSendButton: Boolean,
    onSendClick: () -> Unit,
    content: @Composable RowScope.()->Unit
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        content()

        IconButton(
            onClick = {
                onSendClick()
            },
            enabled = enableSendButton
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}

