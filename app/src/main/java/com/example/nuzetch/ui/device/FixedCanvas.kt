package com.example.nuzetch.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.min

// Lays content out at a fixed design size, then scales it to fit the screen
// with letterbox bars around it, so it looks the same on every device.
@Composable
fun FixedCanvas(
    designWidth: Dp,
    designHeight: Dp,
    letterboxColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(letterboxColor)
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.Center
        ) {
            val scale = min(maxWidth / designWidth, maxHeight / designHeight)
            val density = LocalDensity.current

            // Inside here 1.dp is `scale` times its normal size, so the design size fills the space
            CompositionLocalProvider(
                LocalDensity provides Density(density.density * scale, density.fontScale)
            ) {
                Box(Modifier.size(designWidth, designHeight)) {
                    content()
                }
            }
        }
    }
}
