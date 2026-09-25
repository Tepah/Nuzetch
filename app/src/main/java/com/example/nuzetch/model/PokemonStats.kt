package com.example.nuzetch.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Base stats, named and ordered like PokeAPI's /pokemon/{id} "stats" entries
@Serializable
data class PokemonStats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    @SerialName("special-attack") val specialAttack: Int,
    @SerialName("special-defense") val specialDefense: Int,
    val speed: Int
) {
    val total: Int
        get() = hp + attack + defense + specialAttack + specialDefense + speed
}
