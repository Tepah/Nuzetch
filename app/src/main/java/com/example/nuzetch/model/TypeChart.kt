package com.example.nuzetch.model

import kotlin.collections.mapOf
import kotlin.test.assertEquals

object TypeChart {

    private val chart: Map<PokemonType, Map<PokemonType, Double>> = mapOf(
        PokemonType.NORMAL to mapOf(
            PokemonType.ROCK to 0.5,
            PokemonType.GHOST to 0.0,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.FIRE to mapOf(
            PokemonType.WATER to 0.5,
            PokemonType.GRASS to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.ICE to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.ROCK to 0.5,
            PokemonType.DRAGON to 0.5,
            PokemonType.STEEL to 2.0
        ),
        PokemonType.WATER to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.WATER to 0.5,
            PokemonType.GRASS to 0.5,
            PokemonType.GROUND to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.DRAGON to 0.5
        ),
        PokemonType.ELECTRIC to mapOf(
            PokemonType.WATER to 2.0,
            PokemonType.ELECTRIC to 0.5,
            PokemonType.GRASS to 0.5,
            PokemonType.GROUND to 0.0,
            PokemonType.FLYING to 2.0,
            PokemonType.DRAGON to 0.5
        ),
        PokemonType.GRASS to mapOf(
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 2.0,
            PokemonType.GRASS to 0.5,
            PokemonType.POISON to 0.5,
            PokemonType.GROUND to 2.0,
            PokemonType.FLYING to 0.5,
            PokemonType.BUG to 0.5,
            PokemonType.ROCK to 2.0,
            PokemonType.DRAGON to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.ICE to mapOf(
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 0.5,
            PokemonType.GRASS to 2.0,
            PokemonType.ICE to 0.5,
            PokemonType.GROUND to 2.0,
            PokemonType.FLYING to 2.0,
            PokemonType.DRAGON to 2.0,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.FIGHTING to mapOf(
            PokemonType.NORMAL to 2.0,
            PokemonType.ICE to 2.0,
            PokemonType.POISON to 0.5,
            PokemonType.FLYING to 0.5,
            PokemonType.PSYCHIC to 0.5,
            PokemonType.BUG to 0.5,
            PokemonType.ROCK to 2.0,
            PokemonType.GHOST to 0.0,
            PokemonType.DARK to 2.0,
            PokemonType.STEEL to 2.0,
            PokemonType.FAIRY to 0.5
        ),
        PokemonType.POISON to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.POISON to 0.5,
            PokemonType.GROUND to 0.5,
            PokemonType.ROCK to 0.5,
            PokemonType.GHOST to 0.5,
            PokemonType.STEEL to 0.0,
            PokemonType.FAIRY to 2.0
        ),
        PokemonType.GROUND to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.ELECTRIC to 2.0,
            PokemonType.GRASS to 0.5,
            PokemonType.POISON to 2.0,
            PokemonType.FLYING to 0.0,
            PokemonType.BUG to 0.5,
            PokemonType.ROCK to 2.0,
            PokemonType.STEEL to 2.0
        ),
        PokemonType.FLYING to mapOf(
            PokemonType.ELECTRIC to 0.5,
            PokemonType.GRASS to 2.0,
            PokemonType.FIGHTING to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.ROCK to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.PSYCHIC to mapOf(
            PokemonType.FIGHTING to 2.0,
            PokemonType.POISON to 2.0,
            PokemonType.PSYCHIC to 0.5,
            PokemonType.DARK to 0.0,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.BUG to mapOf(
            PokemonType.FIRE to 0.5,
            PokemonType.GRASS to 2.0,
            PokemonType.FIGHTING to 0.5,
            PokemonType.POISON to 0.5,
            PokemonType.FLYING to 0.5,
            PokemonType.PSYCHIC to 2.0,
            PokemonType.GHOST to 0.5,
            PokemonType.DARK to 2.0,
            PokemonType.STEEL to 0.5,
            PokemonType.FAIRY to 0.5
        ),
        PokemonType.ROCK to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.ICE to 2.0,
            PokemonType.FIGHTING to 0.5,
            PokemonType.GROUND to 0.5,
            PokemonType.FLYING to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.GHOST to mapOf(
            PokemonType.NORMAL to 0.0,
            PokemonType.PSYCHIC to 2.0,
            PokemonType.GHOST to 2.0,
            PokemonType.DARK to 0.5
        ),
        PokemonType.DRAGON to mapOf(
            PokemonType.DRAGON to 2.0,
            PokemonType.STEEL to 0.5,
            PokemonType.FAIRY to 0.0
        ),
        PokemonType.DARK to mapOf(
            PokemonType.FIGHTING to 0.5,
            PokemonType.PSYCHIC to 2.0,
            PokemonType.GHOST to 2.0,
            PokemonType.DARK to 0.5,
            PokemonType.FAIRY to 0.5
        ),
        PokemonType.STEEL to mapOf(
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 0.5,
            PokemonType.ELECTRIC to 0.5,
            PokemonType.ICE to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.STEEL to 0.5,
            PokemonType.FAIRY to 2.0
        ),
        PokemonType.FAIRY to mapOf(
            PokemonType.FIRE to 0.5,
            PokemonType.FIGHTING to 2.0,
            PokemonType.POISON to 0.5,
            PokemonType.DRAGON to 2.0,
            PokemonType.DARK to 2.0,
            PokemonType.STEEL to 0.5
        )
    )

    fun fullEffectivenessOf(attacker: PokemonType): Map<PokemonType, Double> {
        val row = chart.getValue(attacker)
        return PokemonType.entries.associateWith { defender -> row[defender] ?: 1.0}
    }


    fun effectivenessOf(attacker: PokemonType, defenders: List<PokemonType>): Double {
        return defenders
            .map { defender -> chart[attacker]?.get(defender) ?: 1.0}
            .fold(1.0) { acc, multiplier -> acc * multiplier }
    }

    fun weaknessOf(defenders: List<PokemonType>): List<PokemonType> {
        return PokemonType.entries.filter { attacker -> effectivenessOf(attacker, defenders) > 1.0 }
    }

    fun resistancesOf(defenders: List<PokemonType>): List<PokemonType> {
        return PokemonType.entries.filter { attacker ->
            val multiplier = effectivenessOf(attacker, defenders)
            multiplier < 1.0 && multiplier > 0.0
        }
    }

    fun noEffectOf(defenders: List<PokemonType>): List<PokemonType> {
        return PokemonType.entries.filter { attacker -> effectivenessOf(attacker, defenders) == 0.0 }
    }

    fun neutralOf(defenders: List<PokemonType>): List<PokemonType> {
        return PokemonType.entries.filter { attacker -> effectivenessOf(attacker, defenders) == 1.0 }
    }

    // The largest any single weakness/resistance/no-effect/neutral row can get,
    // checked across every reachable 1- or 2-type selection (selection is capped at 2).
    // Computed once and cached so UI can size icons off a real bound instead of a guess.
    val maxResultRowSize: Int by lazy {
        val singleTypeCombos = PokemonType.entries.map { listOf(it) }
        val dualTypeCombos = PokemonType.entries.flatMapIndexed { index, first ->
            PokemonType.entries.drop(index + 1).map { second -> listOf(first, second) }
        }

        (singleTypeCombos + dualTypeCombos).maxOf { defenders ->
            maxOf(
                weaknessOf(defenders).size,
                resistancesOf(defenders).size,
                noEffectOf(defenders).size,
                neutralOf(defenders).size
            )
        }
    }
}

