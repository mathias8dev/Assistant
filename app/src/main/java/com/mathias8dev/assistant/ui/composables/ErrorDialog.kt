package com.mathias8dev.assistant.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mathias8dev.assistant.R



@Composable
fun ErrorDialog(
    @StringRes errorRes: Int,
    onDismissRequest : ()->Unit = {}
) {
    ErrorDialog(
        errorMessage = stringResource(id = errorRes),
        onDismissRequest = onDismissRequest
    )
}


@Composable
fun ErrorDialog(
    errorMessage: String = "An error occurred when trying to process your request",
    onDismissRequest : ()->Unit = {}
) {
    StandardDialog(
        onDismissRequest = onDismissRequest
    ) {
        LottieAnimation(
            animationRes = R.raw.lottie_error,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(72.dp)
        )

        Text(
            text = errorMessage,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
            onClick = onDismissRequest
        ) {
            Text("OK")
        }
    }
}