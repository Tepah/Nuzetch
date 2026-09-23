package com.example.nuzetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nuzetch.ui.device.NuzetchDevice
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
