package com.example.finova.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.finova.ui.theme.GlassmorphismBackground
import com.example.finova.ui.theme.GlassmorphismBorder

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(25.dp),
                ambientColor = GlassmorphismBorder,
                spotColor = GlassmorphismBorder
            )
            .background(GlassmorphismBackground, shape = RoundedCornerShape(25.dp))
            .border(1.dp, GlassmorphismBorder, RoundedCornerShape(25.dp))
    ) {
        content()
    }
}
