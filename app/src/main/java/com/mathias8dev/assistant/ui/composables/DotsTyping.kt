package com.mathias8dev.assistant.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DotsTyping(
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    dotCount: Int = 3,
    animationDelayMillis: Int = 300,
    dotColor: Color = MaterialTheme.colorScheme.primary,
    dotShape: Shape = CircleShape
) {
    val maxOffset = 10f
    val spaceSize = 2.dp

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = dotColor,
                shape = dotShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition(
        label = "dot animation"
    )

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationDelayMillis * dotCount
                0f at delay with LinearEasing
                maxOffset at delay + animationDelayMillis with LinearEasing
                0f at delay + animationDelayMillis * 2
            }
        ),
        label = "dot animation offset"
    )



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = maxOffset.dp)
            .then(modifier)
    ) {


        for (index in 0 until dotCount) {
            val offset by animateOffsetWithDelay(index * animationDelayMillis)
            Dot(offset)
            if (index < dotCount - 1) Spacer(Modifier.width(spaceSize))
        }
    }
}

