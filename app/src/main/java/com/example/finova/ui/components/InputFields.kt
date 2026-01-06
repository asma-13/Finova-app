package com.example.finova.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaGold

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0x800D0D0D),
            unfocusedContainerColor = Color(0x800D0D0D),
            disabledContainerColor = Color(0x800D0D0D),
            unfocusedTextColor = FinovaCream,
            focusedTextColor = FinovaCream,
            focusedIndicatorColor = FinovaGold,
            unfocusedIndicatorColor = FinovaGold.copy(alpha = 0.3f),
            focusedLabelColor = FinovaGold,
            unfocusedLabelColor = FinovaCream.copy(alpha = 0.7f),
            focusedPlaceholderColor = FinovaCream.copy(alpha = 0.4f),
            unfocusedPlaceholderColor = FinovaCream.copy(alpha = 0.4f)
        )
    )
}
