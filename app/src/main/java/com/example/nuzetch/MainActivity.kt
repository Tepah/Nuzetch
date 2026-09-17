package com.example.nuzetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.model.TypeChart
import com.example.nuzetch.model.iconRes
import com.example.nuzetch.ui.theme.NuzetchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NuzetchTheme {
                    WeaknessChart()
            }
        }
    }
}

@Composable
fun WeaknessChart(modifier: Modifier = Modifier) {
    var selectedTypes by remember { mutableStateOf<List<PokemonType>>(emptyList())}

    val weaknesses = TypeChart.weaknessOf(selectedTypes)
    val resistances = TypeChart.resistancesOf(selectedTypes)
    val noEffect = TypeChart.noEffectOf(selectedTypes)
    val neutral = TypeChart.neutralOf(selectedTypes)


    fun onTypeClick(type: PokemonType, isSelected: Boolean) {
        selectedTypes = when {
            isSelected -> selectedTypes - type
            selectedTypes.size < 2 -> selectedTypes + type
            else -> selectedTypes.drop(1) + type
        }
    }

    val (leftTypes, rightTypes) = PokemonType.entries.chunked(9)

    // Caller controls how much space is offered (fillMaxSize, a fixed size, padding, etc.);
    // this always renders as the largest centered 4:3 rectangle that fits within it.
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.aspectRatio(4f / 3f)) {
            TypeZigzag(
                types = leftTypes,
                selectedTypes = selectedTypes,
                onTypeClick = ::onTypeClick,
                modifier = Modifier.weight(0.8f).fillMaxHeight()
            )

            Column(
                modifier = Modifier.weight(1.5f).padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedTypes.isEmpty()) {
                    Text("None Selected")
                } else {
                    Text("Weaknesses: $weaknesses")

                    if (resistances.isNotEmpty()) {
                        Text("Resistances: $resistances")
                    }

                    if (noEffect.isNotEmpty()) {
                        Text("No Effect: $noEffect")
                    }

                    if (neutral.isNotEmpty()) {
                        Text("Neutral to: $neutral")
                    }
                }
            }

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
private fun WeaknessDistribution(
    weakness: List<PokemonType>,
    noEffect: List<PokemonType>,
    resistances: List<PokemonType>,
    normal: List<PokemonType>
)

@Composable
private fun TypeZigzag(
    types: List<PokemonType>,
    selectedTypes: List<PokemonType>,
    onTypeClick: (PokemonType, Boolean) -> Unit,
    startFromEnd: Boolean = false,
    modifier: Modifier = Modifier
) {
    /*
        Function Creates a zigzag pattern for the list of pokemon types
        startFromEnd decides whether the type is starting from the left or right.
     */
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

@Composable
private fun TypeIcon(
    type: PokemonType,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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

@Preview(showBackground = true, widthDp = 1240, heightDp = 1080, name = "Thor Bottom")
@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "Phone")
@Composable
fun GreetingPreview() {
    NuzetchTheme {
        WeaknessChart()
    }
}