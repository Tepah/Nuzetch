package com.example.nuzetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuzetch.model.PokemonType
import com.example.nuzetch.ui.theme.NuzetchTheme
import com.example.nuzetch.model.TypeChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NuzetchTheme {
                    TypeTesting()
            }
        }
    }
}

@Composable
fun TypeTesting() {
    val type1 = PokemonType.NORMAL

    val against = listOf(PokemonType.ELECTRIC)
    val effectiveness = TypeChart.effectivenessOf(type1, against)

    Surface(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$type1 vs $against = ${effectiveness}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NuzetchTheme {
        TypeTesting()
    }
}