package com.mathias8dev.assistant.ui.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun LottieAnimation(
    modifier: Modifier = Modifier.size(72.dp),
    @RawRes animationRes: Int,
    repeatForever: Boolean = false
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (repeatForever) Int.MAX_VALUE else 1
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
    )
}