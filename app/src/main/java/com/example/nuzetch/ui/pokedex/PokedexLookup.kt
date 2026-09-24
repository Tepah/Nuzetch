package com.example.nuzetch.ui.pokedex

import android.R.attr.onClick
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuzetch.R
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import com.example.nuzetch.model.PokedexEntry
import com.example.nuzetch.ui.components.TypeIcon
import kotlinx.serialization.json.Json
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.ui.components.iconRes
import com.example.nuzetch.ui.device.NuzetchDevice
import com.example.nuzetch.ui.theme.NuzetchTheme


@Composable
fun PokedexLookup() {
    val context = LocalContext.current
    val allPokemon = remember {
        val text = context.assets.open("pokemon.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<PokedexEntry>>(text)
    }
    var currentPokemon: PokedexEntry? by remember {mutableStateOf(null)}
    var showMoveList by remember {mutableStateOf(true)}

    fun onRowClick (pokemon: PokedexEntry) {
        currentPokemon = pokemon
    }

    // Pokemon on the left
    Row(Modifier
        .padding(horizontal = 16.dp)) {
        // Column for the Pokemon Icon
        Box(Modifier
            .weight(3f)
            .padding(32.dp)) {
            Column(Modifier) {
                Box(
                    Modifier
                )
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
            // Column for Hud
            Column(Modifier) {
                Box(
                    Modifier
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(16.dp),

                )
                {
                    currentPokemon?.let { Text(it.name.replaceFirstChar { it.uppercase() }, fontSize = 64.sp, maxLines = 10) }
                }
                Box(
                    Modifier
                        .padding(vertical = 16.dp)
                        .weight(5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                )
                {
                    Box(Modifier
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(24.dp))
                        .padding(16.dp)
                        .clickable(onClick={}))
                    {
                        Text("...", fontSize = 32.sp)
                    }
                }
                Box(
                    Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(24.dp))
                ) {
                }

            }
        }


        Box (Modifier
            .weight(3f))
        {
            LazyColumn(Modifier
                .padding(horizontal = 48.dp)) {
                items(allPokemon, key = { it.id }) { pokemon ->
                    PokedexRow(pokemon, ::onRowClick)
                }
            }

            if (showMoveList) {
                Surface(
                    Modifier
                        .padding(horizontal = 8.dp, vertical = 32.dp)
                        .fillMaxSize()
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(24.dp))
                )
                {

                }
            }
        }


    }


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