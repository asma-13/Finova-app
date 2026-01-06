package com.example.finova.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import com.example.finova.R
import com.example.finova.ui.theme.FinovaGold

@Composable
fun FinovaLogo(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.finova_logo),
            contentDescription = "Finova Logo",
            modifier = Modifier.drawBehind {
                drawIntoCanvas {
                    val color = FinovaGold.toArgb()
                    val frameworkPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
                        setShadowLayer(
                            30f, // blur radius
                            0f,  // offset x
                            0f,  // offset y
                            color
                        )
                        this.color = android.graphics.Color.TRANSPARENT
                    }
                    it.nativeCanvas.drawCircle(center.x, center.y, size.minDimension / 2, frameworkPaint)
                }
            }
        )
    }
}
