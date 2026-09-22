package com.example.nuzetch

import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
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
                    NuzetchDevice()
            }
        }
    }
}

@Composable
fun NuzetchDevice(modifier: Modifier = Modifier) {
    var onApp by remember { mutableIntStateOf(0) }
    val appCount = 4

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF646464)
    ) {
        Column( modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .height(192.dp)
                        .width(384.dp),
                    onClick = ({})
                )
                {}

                Row(
                    modifier = Modifier
                        .padding(top = 64.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                )
                {
                    for (i in 0 until appCount) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        )
                        {}
                    }
                }

                Button(
                    modifier = Modifier
                        .height(192.dp)
                        .width(384.dp),
                    onClick = ({})
                )
                {}
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(32.dp))
                    .fillMaxSize()
                    .innerShadow(
                        shape = RoundedCornerShape(32.dp),
                        shadow = Shadow(
                            radius = 8.dp,
                            spread = 2.dp,
                            color = Color(0x66000000),
                            offset = DpOffset(8.dp, 6.dp)
                        )
                    )
            )
            {

            }
        }
    }
}


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
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.aspectRatio(4f / 3f)) {
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
private fun WeaknessDistribution(
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

@Composable
private fun TypeIcon(
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
private fun AnimatedTypeIcon(type: PokemonType, iconSize: Dp) {
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

@Preview(showBackground = true, widthDp = 1240, heightDp = 1080, name = "Thor Bottom")
@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "Phone")
@Composable
fun NuzetchPreview() {
    NuzetchTheme {
        NuzetchDevice()
    }
}

@Preview(showBackground = true, widthDp = 1240, heightDp = 1080, name = "Thor Bottom")
@Composable
fun WeaknessPreview() {
    NuzetchTheme {
        WeaknessChart()
    }
}