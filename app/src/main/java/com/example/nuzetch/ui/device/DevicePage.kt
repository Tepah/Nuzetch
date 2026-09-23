package com.example.nuzetch.ui.device

// The pages the device can cycle through, in on-screen order.
// Adding a page here adds a dot to the indicator and forces NuzetchDevice to handle it.
enum class DevicePage {
    WEAKNESS,
    POKEDEX,
    OTHER,
    CALCULATOR;

    // Wraps around at both ends, so stepping past the last page returns to the first.
    fun step(direction: Int): DevicePage = entries[(ordinal + direction).mod(entries.size)]
}
