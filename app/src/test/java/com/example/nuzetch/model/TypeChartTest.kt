package com.example.nuzetch.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeChartTest {

    @Test
    fun `water is super effective against fire`() {
        assertEquals(2.0, TypeChart.effectivenessOf(PokemonType.WATER, listOf(PokemonType.FIRE)), 0.0)
    }

    @Test
    fun `water is normal to fighting`() {
        assertEquals(1.0, TypeChart.effectivenessOf(PokemonType.WATER, listOf(PokemonType.FIGHTING)), 0.0)
    }

    @Test
    fun `Electric is not effective to ground`() {
        assertEquals(0.0, TypeChart.effectivenessOf(PokemonType.ELECTRIC, listOf(PokemonType.GROUND)), 0.0)
    }

    @Test
    fun `Fire is 4x effective against grass - bug`() {
        assertEquals(4.0, TypeChart.effectivenessOf(PokemonType.FIRE, listOf(PokemonType.GRASS,
            PokemonType.BUG)), 0.0)
    }
}
