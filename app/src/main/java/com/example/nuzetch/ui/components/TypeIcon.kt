package com.example.nuzetch.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.nuzetch.model.PokemonType

@Composable
fun TypeIcon(
    type: PokemonType,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(id = type.iconRes),
        contentDescription = type.name,
        contentScale = ContentScale.Fit,
        alpha = if (isSelected) 1f else 0.4f,
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    )
}

// Creates an animated type icon that fades in from the top of it's set location when a new one gets
// generated.
@Composable
fun AnimatedTypeIcon(type: PokemonType, iconSize: Dp) {
    key(type) {
        val visibleState = remember { MutableTransitionState(false) }
        visibleState.targetState = true

        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)) +
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight / 3 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            TypeIcon(
                type = type,
                isSelected = true,
                modifier = Modifier.requiredSize(iconSize)
            )
        }
    }
}
