package com.example.nuzetch.model

import androidx.compose.ui.graphics.PathSegment
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

    @Test
    fun `fullEffectivenessOf returns an entry for every type`() {
        val result = TypeChart.fullEffectivenessOf(PokemonType.WATER)
        assertEquals(PokemonType.entries.size, result.size)
    }


    @Test
    fun `fullEffectivenessOf returns water's known multis`() {
        val result = TypeChart.fullEffectivenessOf(PokemonType.WATER)
        assertEquals(2.0, result[PokemonType.FIRE])
        assertEquals(0.5, result[PokemonType.WATER])
        assertEquals(0.5, result[PokemonType.GRASS])
        assertEquals(2.0, result[PokemonType.GROUND])
        assertEquals(2.0, result[PokemonType.ROCK])
        assertEquals(0.5, result[PokemonType.DRAGON])
    }

    @Test
    fun `fullEffectivenessOf defaults unlisted types to neutral`() {
        val result = TypeChart.fullEffectivenessOf(PokemonType.WATER)
        assertEquals(1.0, result[PokemonType.NORMAL])
    }

    @Test
    fun `weaknessOf returns all Water weaknesses`() {
        val result = TypeChart.weaknessOf(listOf(PokemonType.WATER))
        assertEquals(2, result.size)
        assertEquals(2.0, result[PokemonType.GRASS])
        assertEquals(2.0, result[PokemonType.ELECTRIC])
    }

    @Test
    fun `grass is not a weakness of fire`() {
        val res = TypeChart.weaknessOf(listOf(PokemonType.FIRE))
        assertFalse(PokemonType.GRASS in res)
    }

    @Test
    fun `resistances of Ground`(){
        val res = TypeChart.resistancesOf(listOf(PokemonType.GROUND))
        assertTrue(PokemonType.POISON in res)
        assertTrue(PokemonType.ROCK in res)
    }

    @Test
    fun `normal to Water`() {
        val res = TypeChart.neutralOf(listOf(PokemonType.WATER))
        assertTrue(PokemonType.FIGHTING in res)
        assertTrue(PokemonType.DARK in res)
    }

    @Test
    fun `Electric is no Effect on ground`() {
        assertTrue(PokemonType.ELECTRIC in TypeChart.noEffectOf(listOf(PokemonType.GROUND)))
    }
}
