package com.example.nuzetch.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.nuzetch.ui.pokedex.PokedexLookup
import com.example.nuzetch.ui.theme.NuzetchTheme
import com.example.nuzetch.ui.weakness.WeaknessChart

@Composable
fun NuzetchDevice(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableStateOf(DevicePage.POKEDEX) }

    // Everything below is designed at 1240x1080dp and scaled to fit any screen
    FixedCanvas(
        designWidth = 1240.dp,
        designHeight = 1080.dp,
        letterboxColor = Color(0xFF3A3A3A)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF646464)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier
                            .height(164.dp)
                            .width(384.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { currentPage = currentPage.step(-1) }
                    )
                    {}

                    PageIndicator(currentPage = currentPage)

                    Button(
                        modifier = Modifier
                            .height(164.dp)
                            .width(384.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { currentPage = currentPage.step(1) }
                    )
                    {}
                }

                Box(
                    modifier = Modifier
                        .padding(start=16.dp, end=16.dp, bottom=16.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(32.dp))
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
                    // The screen isn't a Surface, so tell its text which color to use
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                        when (currentPage) {
                            DevicePage.WEAKNESS -> WeaknessChart()
                            DevicePage.POKEDEX -> PokedexLookup()
                            DevicePage.OTHER -> Text("Other page")
                            DevicePage.CALCULATOR -> Text("Calculator")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(currentPage: DevicePage) {
    Row(
        modifier = Modifier
            .padding(top = 64.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        for (page in DevicePage.entries) {
            if (page == currentPage) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF06FF09))
                        .padding(6.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .padding(6.dp)
                )
            }
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
