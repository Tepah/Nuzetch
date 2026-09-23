package com.example.nuzetch.ui.weakness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.model.TypeChart
import com.example.nuzetch.ui.components.TypeIcon
import com.example.nuzetch.ui.theme.NuzetchTheme

@Composable
fun WeaknessChart(modifier: Modifier = Modifier) {
    var selectedTypes by remember { mutableStateOf<List<PokemonType>>(emptyList())}

    val doubleWeaknesses = TypeChart.doubleWeaknessOf(selectedTypes)
    val weaknesses = TypeChart.weaknessOf(selectedTypes)
    val resistances = TypeChart.resistancesOf(selectedTypes)
    val noEffect = TypeChart.noEffectOf(selectedTypes)
    val neutral = TypeChart.neutralOf(selectedTypes)

    val maxResultTypes = remember { TypeChart.maxResultRowSize }


    fun onTypeClick(type: PokemonType, isSelected: Boolean) {
        selectedTypes = when {
            isSelected -> selectedTypes - type
            selectedTypes.size < 2 -> selectedTypes + type
            else -> selectedTypes.drop(1) + type
        }
    }

    val (leftTypes, rightTypes) = PokemonType.entries.chunked(9)

    // Sets the row colors
    val resultRowColors = listOf(
        Color(0xFF43A047).copy(alpha = 0.7f),  // 4x - vivid green
        Color(0xFFA5D6A7).copy(alpha = 0.55f), // 2x - soft green
        Color(0xFFE0E0E0).copy(alpha = 0.55f), // 1x - neutral gray
        Color(0xFFE57373).copy(alpha = 0.55f), // 0.5x - red
        Color(0xFFB0BEC5).copy(alpha = 0.55f), // 0x - neutral slate
    )

    // Box that always renders at 4/3 to keep aspect ratio regardless of device.
    Box(modifier = modifier.fillMaxSize().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier) {
            TypeZigzag(
                types = leftTypes,
                selectedTypes = selectedTypes,
                onTypeClick = ::onTypeClick,
                modifier = Modifier.weight(0.8f).fillMaxHeight()
            )

            ColumnDivider()

            WeaknessDistribution(
                doubleWeakness = doubleWeaknesses,
                weakness = weaknesses,
                resistances = resistances,
                neutral = neutral,
                noEffect = noEffect,
                selectedTypes = selectedTypes,
                maxResultTypes = maxResultTypes,
                resultRowColors = resultRowColors,
                modifier = Modifier.weight(1.6f).padding(horizontal = 8.dp)
            )

            ColumnDivider()

            TypeZigzag(
                types = rightTypes,
                selectedTypes = selectedTypes,
                onTypeClick = ::onTypeClick,
                startFromEnd = true,
                modifier = Modifier.weight(0.8f).fillMaxHeight()
            )
        }
    }
}

@Composable
private fun ColumnDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 24.dp)
            .width(1.dp)
            .background(Color.Gray)
    )
}

@Composable
private fun TypeZigzag(
    types: List<PokemonType>,
    selectedTypes: List<PokemonType>,
    onTypeClick: (PokemonType, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    startFromEnd: Boolean = false,
) {
    // Creates a zigzag pattern to the left and right columns
    BoxWithConstraints(modifier = modifier) {
        val iconSize = (maxHeight / types.size) * 1.5f

        Column(modifier = Modifier.fillMaxSize()) {
            types.forEachIndexed { index, type ->
                val isSelected = type in selectedTypes
                val alignRight = if (startFromEnd) index % 2 == 0 else index % 2 != 0

                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = BiasAlignment(
                        horizontalBias = if (alignRight) 0.7f else -0.7f,
                        verticalBias = 0f
                    )
                ) {
                    TypeIcon(
                        type = type,
                        isSelected = isSelected,
                        modifier = Modifier.requiredSize(iconSize)
                    ) { onTypeClick(type, isSelected) }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1240, heightDp = 1080, name = "Thor Bottom")
@Composable
fun WeaknessPreview() {
    NuzetchTheme {
        WeaknessChart()
    }
}
