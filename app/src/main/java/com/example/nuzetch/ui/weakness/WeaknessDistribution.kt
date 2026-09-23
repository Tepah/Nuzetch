package com.example.nuzetch.ui.weakness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.ui.components.AnimatedTypeIcon

@Composable
internal fun WeaknessDistribution(
    doubleWeakness: List<PokemonType>,
    weakness: List<PokemonType>,
    noEffect: List<PokemonType>,
    resistances: List<PokemonType>,
    neutral: List<PokemonType>,
    selectedTypes: List<PokemonType>,
    maxResultTypes: Int,
    resultRowColors: List<Color>,
    modifier: Modifier = Modifier
) {
    // false = show weaknesses (4x, 2x, 1x), true = show resistances (0.5x, 0x)
    var isResistanceMode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val stackedRowColors = if (isResistanceMode) {
            listOf(resultRowColors[3], resultRowColors[4])
        } else {
            listOf(resultRowColors[0], resultRowColors[1], resultRowColors[2])
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(rowStackGradient(stackedRowColors))
        ) {
            if (isResistanceMode) {
                ResultRow(
                    label = "0.5x",
                    types = resistances,
                    maxResultTypes = maxResultTypes,
                    modifier = Modifier.weight(1f)
                )
                ResultRow(
                    label = "0x",
                    types = noEffect,
                    maxResultTypes = maxResultTypes,
                    modifier = Modifier.weight(1f)
                )
            } else {
                ResultRow(
                    label = "4x",
                    types = doubleWeakness,
                    maxResultTypes = maxResultTypes,
                    modifier = Modifier.weight(1f)
                )
                ResultRow(
                    label = "2x",
                    types = weakness,
                    maxResultTypes = maxResultTypes,
                    modifier = Modifier.weight(1f)
                )
                ResultRow(
                    label = "1x",
                    types = neutral,
                    visible = selectedTypes.isNotEmpty() && neutral.isNotEmpty(),
                    maxResultTypes = maxResultTypes,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        WeaknessResistanceSwitch(
            isResistanceMode = isResistanceMode,
            onModeChange = { isResistanceMode = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(stackedRowColors.last())
                .padding(top = 8.dp, bottom = 4.dp)
        )
    }
}

// Builds one continuous gradient across a whole row stack, with each row's color
// centered on its own slice, so neighboring rows blend into each other instead of
// each row fading independently and leaving a hard seam at the shared edge.
private fun rowStackGradient(colors: List<Color>): Brush {
    val colorStops = colors.mapIndexed { index, color ->
        ((index + 0.5f) / colors.size) to color
    }
    return Brush.verticalGradient(*colorStops.toTypedArray())
}

@Composable
private fun ResultRow(
    label: String,
    types: List<PokemonType>,
    maxResultTypes: Int,
    modifier: Modifier = Modifier,
    visible: Boolean = types.isNotEmpty(),
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.TopStart).padding(4.dp)
        )
        if (visible) {
            val iconSize = (maxHeight / maxResultTypes) * 6f
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                types.forEach { type ->
                    AnimatedTypeIcon(type = type, iconSize = iconSize)
                }
            }
        }
    }
}

@Composable
private fun WeaknessResistanceSwitch(
    isResistanceMode: Boolean,
    onModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Text(text = "Weak", fontWeight = if (!isResistanceMode) FontWeight.Bold else FontWeight.Normal)
        Switch(checked = isResistanceMode, onCheckedChange = onModeChange)
        Text(text = "Resist", fontWeight = if (isResistanceMode) FontWeight.Bold else FontWeight.Normal)
    }
}
