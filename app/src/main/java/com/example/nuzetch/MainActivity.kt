package com.example.nuzetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.model.TypeChart
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
fun WeaknessChart() {
    var selectedTypes by remember { mutableStateOf<List<PokemonType>>(emptyList())}

    val weaknesses = TypeChart.weaknessOf(selectedTypes)
    val resistances = TypeChart.resistancesOf(selectedTypes)
    val noEffect = TypeChart.noEffectOf(selectedTypes)
    val neutral = TypeChart.noEffectOf(selectedTypes)


    fun onTypeClick(type: PokemonType, isSelected: Boolean) {
        selectedTypes = when {
            isSelected -> selectedTypes - type
            selectedTypes.size < 2 -> selectedTypes + type
            else -> selectedTypes.drop(1) + type
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(PokemonType.entries) { type ->
                val isSelected = type in selectedTypes
                val backgroundColor = if (isSelected) Color(0xFF2FAE64) else Color(0xFFE0E0E0)

                Text(
                    text = type.name,
                    color = if(isSelected) Color.White else Color.Black,
                    modifier = Modifier
                        .clickable{onTypeClick(type, isSelected)}
                        .background(backgroundColor)
                        .padding(8.dp)
                )
            }
        }

        if (weaknesses.isEmpty()) {
            Text("None Selected")
        } else {
            Text("Weaknesses: $weaknesses")
        }

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NuzetchTheme {
        WeaknessChart()
    }
}