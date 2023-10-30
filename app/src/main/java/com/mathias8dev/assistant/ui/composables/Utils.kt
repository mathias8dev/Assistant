package com.mathias8dev.assistant.ui.composables

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextAfterSelection
import androidx.compose.ui.text.input.getTextBeforeSelection


@Stable
fun Modifier.useModifierIf(
    condition: Boolean,
    callback: (currentModifier: Modifier) -> Modifier
): Modifier {
    return if (condition) callback(this) else this
}




fun insertText(textFieldValue: TextFieldValue, text: String): TextFieldValue {
    val maxChars = textFieldValue.text.length
    val textBeforeSelection = textFieldValue.getTextBeforeSelection(maxChars)
    val textAfterSelection = textFieldValue.getTextAfterSelection(maxChars)
    val newText = "$textBeforeSelection$text$textAfterSelection"
    val newCursorPosition = textBeforeSelection.length + text.length
    return TextFieldValue(
        text = newText,
        selection = TextRange(newCursorPosition)
    )
}

