package com.example.finova.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.finova.ui.theme.FinovaBlack
import com.example.finova.ui.theme.FinovaGold

@Composable
fun FilledGoldButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = FinovaGold,
                spotColor = FinovaGold
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = FinovaGold,
            contentColor = FinovaBlack,
            disabledContainerColor = FinovaGold.copy(alpha = 0.38f),
            disabledContentColor = FinovaBlack.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(text = text.uppercase())
    }
}

@Composable
fun OutlinedGoldButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = FinovaGold
        ),
        border = BorderStroke(2.dp, FinovaGold),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(text = text.uppercase())
    }
}
