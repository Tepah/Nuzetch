package com.example.nuzetch.model

import androidx.annotation.DrawableRes
import com.example.nuzetch.R

@get:DrawableRes
val PokemonType.iconRes: Int
    get() = when (this) {
        PokemonType.NORMAL -> R.drawable.types_normal
        PokemonType.FIRE -> R.drawable.types_fire
        PokemonType.WATER -> R.drawable.types_water
        PokemonType.ELECTRIC -> R.drawable.types_electric
        PokemonType.GRASS -> R.drawable.types_grass
        PokemonType.ICE -> R.drawable.types_ice
        PokemonType.FIGHTING -> R.drawable.types_fighting
        PokemonType.POISON -> R.drawable.types_poison
        PokemonType.GROUND -> R.drawable.types_ground
        PokemonType.FLYING -> R.drawable.types_flying
        PokemonType.PSYCHIC -> R.drawable.types_psychic
        PokemonType.BUG -> R.drawable.types_bug
        PokemonType.ROCK -> R.drawable.types_rock
        PokemonType.GHOST -> R.drawable.types_ghost
        PokemonType.DRAGON -> R.drawable.types_dragon
        PokemonType.DARK -> R.drawable.types_dark
        PokemonType.STEEL -> R.drawable.types_steel
        PokemonType.FAIRY -> R.drawable.types_fairy
    }