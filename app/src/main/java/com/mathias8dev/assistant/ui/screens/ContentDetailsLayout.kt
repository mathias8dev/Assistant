package com.mathias8dev.assistant.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ContentDetailsLayout(
    modifier: Modifier = Modifier,
    onBackClick: ()->Unit,
    @StringRes titleRes: Int,
    content: @Composable ColumnScope.()->Unit
) {
    ContentDetailsLayout(
        modifier = modifier,
        onBackClick = onBackClick,
        title = stringResource(id = titleRes),
        content = content
    )
}

@Composable
fun ContentDetailsLayout(
    modifier: Modifier = Modifier,
    onBackClick: ()->Unit,
    title: String,
    content: @Composable ColumnScope.()->Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back icon"
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 32.dp)
                    .width(0.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }

        content()
    }
}