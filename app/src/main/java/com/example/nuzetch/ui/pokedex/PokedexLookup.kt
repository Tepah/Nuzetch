package com.example.nuzetch.ui.pokedex

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuzetch.R
import com.example.nuzetch.model.PokedexEntry
import com.example.nuzetch.model.PokemonStats
import com.example.nuzetch.ui.components.TypeIcon
import com.example.nuzetch.ui.device.NuzetchDevice
import com.example.nuzetch.ui.theme.NuzetchTheme
import kotlinx.serialization.json.Json
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp


@Composable
fun PokedexLookup() {
    val context = LocalContext.current
    val allPokemon = remember {
        val text = context.assets.open("pokemon.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<PokedexEntry>>(text)
    }

    var currentPokemon: PokedexEntry? by remember {mutableStateOf(null)}
    // TODO: Take out temporary Stats
    val curPokemonStats: PokemonStats = PokemonStats(100, 20, 20, 20, 20, 20)
    var showMoveList by remember {mutableStateOf(true)}

    Row(Modifier
        .padding(horizontal = 16.dp)) {
        // Left half: portrait with the HUD drawn over it
        Box(Modifier
            .weight(3f)
            .padding(32.dp)) {
            PokemonPortrait()
            PokemonHud(
                pokemon = currentPokemon,
                pokemonStats = curPokemonStats,
                onMoveButtonClick = { showMoveList = !showMoveList }
            )
        }

        // Right half: pokemon list with the move list sliding over it
        Box (Modifier
            .weight(3f))
        {
            PokemonList(allPokemon, onRowClick = { currentPokemon = it })
            MoveListPanel(visible = showMoveList)
        }
    }
}

// Shared look for every card on the device: rounded, bordered, with a shadow
@Composable
fun DeviceCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp,
        border = BorderStroke(2.dp, Color.Gray),
        content = content
    )
}

@Composable
fun DeviceCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp,
        border = BorderStroke(2.dp, Color.Gray),
        content = content
    )
}

// Folder-tab border: the top corners can be rounded separately, and the bottom edge
// can be left open. Radii should match the RoundedCornerShape used for the tab.
fun Modifier.tabBorder(
    width: Dp,
    color: Color,
    topStartRadius: Dp,
    topEndRadius: Dp,
    drawBottom: Boolean
) = drawWithContent {
    drawContent()
    val strokeWidth = width.toPx()
    val inset = strokeWidth / 2
    val startRadius = (topStartRadius.toPx() - inset).coerceAtLeast(0f)
    val endRadius = (topEndRadius.toPx() - inset).coerceAtLeast(0f)
    val left = inset
    val right = size.width - inset
    // An open bottom runs the sides to the very edge so they meet whatever sits below
    val bottom = if (drawBottom) size.height - inset else size.height
    val path = Path().apply {
        moveTo(left, bottom)
        lineTo(left, inset + startRadius)
        if (startRadius > 0f) {
            arcTo(
                rect = Rect(left, inset, left + 2 * startRadius, inset + 2 * startRadius),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }
        lineTo(right - endRadius, inset)
        if (endRadius > 0f) {
            arcTo(
                rect = Rect(right - 2 * endRadius, inset, right, inset + 2 * endRadius),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }
        lineTo(right, bottom)
        if (drawBottom) close()
    }
    drawPath(path, color, style = Stroke(strokeWidth))
}

@Composable
fun PokemonPortrait(modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(
            Modifier
                .padding(16.dp)
                .weight(5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "temporary",
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
fun PokemonHud(
    pokemon: PokedexEntry?,
    pokemonStats: PokemonStats?,
    onMoveButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        NameCard(pokemon)
        Box(
            Modifier
                .padding(vertical = 16.dp)
                .weight(5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        )
        {
            MoveButton(onMoveButtonClick)
        }
        StatsCard(
            Modifier
                .weight(2f)
                .fillMaxWidth(),
            pokemonStats = pokemonStats
        )
    }
}

@Composable
fun NameCard(pokemon: PokedexEntry?, modifier: Modifier = Modifier) {
    DeviceCard(modifier) {
        pokemon?.let { Text(it.name.replaceFirstChar { it.uppercase() }, Modifier.padding(16.dp), fontSize = 64.sp, maxLines = 10) }
    }
}

@Composable
fun MoveButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    DeviceCard(onClick = onClick, modifier = modifier) {
        Text(
            "...",
            Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatsCard(modifier: Modifier = Modifier, pokemonStats: PokemonStats?) {
    DeviceCard(modifier) {
        Row(
            modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(modifier.padding(16.dp)) {
                pokemonStats?.let({ Stat("BASE", pokemonStats.total) })
            }
            Column(modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
                pokemonStats?.let({ Stat("HP", pokemonStats.hp) })
                pokemonStats?.let({ Stat("SPD", pokemonStats.speed)})
            }
            Column(modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
                pokemonStats?.let({ Stat("ATT", pokemonStats.attack) })
                pokemonStats?.let({ Stat("DEF", pokemonStats.defense)})
            }
            Column(modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
                pokemonStats?.let({ Stat("SpA", pokemonStats.specialAttack) })
                pokemonStats?.let({ Stat("SpD", pokemonStats.specialDefense)})
            }
        }
    }
}

@Composable
fun Stat(type: String, number: Int, modifier: Modifier = Modifier) {
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(type, fontSize=if (type == "BASE") 36.sp else 32.sp, fontWeight=FontWeight.SemiBold)
        Text(number.toString(), fontSize=32.sp)
    }
}

@Composable
fun PokemonList(
    allPokemon: List<PokedexEntry>,
    onRowClick: (PokedexEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier
        .padding(horizontal = 48.dp)) {
        items(allPokemon, key = { it.id }) { pokemon ->
            PokedexRow(pokemon, onRowClick)
        }
    }
}

@Composable
fun MoveListPanel(visible: Boolean) {
    val slideSpring = spring<IntOffset>(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow)

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { 2500 }, animationSpec = slideSpring),
        exit = slideOutVertically(targetOffsetY = { 2500 }, animationSpec = slideSpring),
    )
    {
        DeviceCard(
            Modifier
                .padding(horizontal = 8.dp, vertical = 32.dp)
                .fillMaxSize(),
        )
        {
            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End)
                {
                    Text("X", Modifier.padding(top = 32.dp, end = 32.dp).clickable {}, fontSize = 32.sp)
                }

                val movesColor = Color(0xFFF4A7A3)
                val abilitiesColor = Color(0xFFA7C7F4)
                val varietiesColor = Color(0xFFB5E3A8)

                Row(Modifier.fillMaxWidth()) {
                    TabButton(tabName = "Moves", color = movesColor, selected = true)
                    TabButton(tabName = "Abilities", color = abilitiesColor, selected = false, middle = true)
                    //Todo: Make so that only shows if there are more forms
                    TabButton(tabName = "Varieties", color = varietiesColor, selected = false, middle = true)
                }

                // Same color as the selected tab so the two read as one folder
                Surface(Modifier.fillMaxSize(), color = movesColor, shape = RoundedCornerShape(topEnd = 16.dp)) {

                }
            }
        }
    }
}

@Composable
fun TabButton(modifier: Modifier = Modifier, tabName: String, color: Color, selected: Boolean, middle: Boolean = false) {
    val topStartRadius = if (middle) 16.dp else 0.dp
    val topEndRadius = 16.dp
    Surface(modifier,
        color = color,
        shape = RoundedCornerShape(topStart = topStartRadius, topEnd = topEndRadius) ) { Text(tabName,
        Modifier.padding(24.dp), fontSize = 32.sp) }
}


// Pokedex number on the left, name in the middle, type icons on the right
@Composable
fun PokedexRow(pokemon: PokedexEntry, onRowClick: (PokedexEntry) -> Unit) {
    Row(
        Modifier
            .padding(vertical = 12.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = {onRowClick(pokemon)}),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("#%04d".format(pokemon.id), fontSize = 32.sp, color = Color.Gray)
        Text(
            pokemon.name.replaceFirstChar { it.uppercase() },
            fontSize = 36.sp,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        pokemon.types.forEach { type ->
            TypeIcon(type, isSelected = true, modifier = Modifier.size(48.dp))
        }
    }
}



@Preview(showBackground = true, widthDp = 1240, heightDp = 1080, name = "Thor Bottom")
@Composable
fun PokedexPreview()
{
    NuzetchTheme {
        NuzetchDevice()
    }
}