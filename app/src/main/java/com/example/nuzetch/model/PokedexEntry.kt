package com.example.nuzetch.model

import kotlinx.serialization.Serializable

@Serializable
data class PokedexEntry(
    val id: Int,
    val name: String,
    val types: List<PokemonType>
)
