package de.schlangguru.restui.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ExtensionsTest {

    @Test
    fun mapIf_shouldNotMap() {
        val mapper = { arg: Int ->  arg + 1 }
        val iterable = listOf(1, 2, 3)

        val expected = listOf(1, 2, 3)
        val actual = iterable.mapIf({ _ -> false }, mapper)
        assertEquals(expected, actual)
    }

    @Test
    fun mapIf_shouldMapAll() {
        val mapper = { arg: Int ->  arg + 1 }
        val iterable = listOf(1, 2, 3)

        val expected = listOf(2, 3, 4)
        val actual = iterable.mapIf({ _ -> true }, mapper)
        assertEquals(expected, actual)
    }

    @Test
    fun mapIf_shouldMapOnce() {
        val mapper = { arg: Int ->  arg + 1 }
        val iterable = listOf(1, 2, 3)

        val expected = listOf(1, 3, 3)
        val actual = iterable.mapIf({ arg -> arg == 2 }, mapper)
        assertEquals(expected, actual)
    }

}