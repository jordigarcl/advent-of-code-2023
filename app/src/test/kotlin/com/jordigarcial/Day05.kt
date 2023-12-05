package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day05 {

    fun solvePuzzle1(input: List<String>): Int {
        return TODO("")
    }

    var regex = Regex("""
        (\w+)-to-(\w+)\s*map:\s*((?:\d+\s*)+)
    """.trimIndent())

    fun solvePuzzle2(input: List<String>): Int {
        return TODO("")
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day05-example1.txt,13",
            "/day05-input1.txt,23235"
        ]
    )
    fun testPuzzle1(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle1(input))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day05-example1.txt,30",
            "/day05-input1.txt,5920640"
        ]
    )
    fun testPuzzle2(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle2(input))
    }
}
