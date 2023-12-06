package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day05 {

    fun solvePuzzle1(input: List<String>): Int {
        val entireInput = input.joinToString("\n")

        val seeds = regexSeeds.matchEntire(entireInput)!!.groupValues[1].split(" ").map { it.toInt() }.toSet()
        val srcToDst = mutableMapOf<String, String>()
        val srcToMappings = mutableMapOf<String, MutableMap<Int, Int>>()

        regexMappingsHeading.findAll(entireInput).forEach { matchResult ->
            val src = matchResult.groupValues[1]
            val dst = matchResult.groupValues[2]
            srcToDst += src to dst
            regexMappingsRow.findAll(matchResult.groupValues[3]).forEach { matchResult ->
                val dstId = matchResult.groupValues[1].toInt()
                val srcId = matchResult.groupValues[2].toInt()
                val range = matchResult.groupValues[3].toInt()
                for (i in 0 until range) {
                    val mappings = srcToMappings.computeIfAbsent(src) { mutableMapOf() }
                    mappings += (srcId + i) to (dstId + i)
                }
            }
        }

        return seeds.minOf { seed ->
            println("=== evaluating $seed ===")
            evaluateLocationDstNumber(srcToDst, srcToMappings, srcNumber = seed)
        }
    }

    private fun evaluateLocationDstNumber(
        srcToDst: MutableMap<String, String>,
        srcToMappings: Map<String, Map<Int, Int>>,
        srcNumber: Int,
        src: String = "seed"
    ): Int {
        println(srcNumber)
        println(src)

        if (src == "location") {
            return srcNumber
        }

        val dstNumber = srcToMappings[src]!![srcNumber] ?: srcNumber
        val dstSrc = srcToDst[src]!!
        return evaluateLocationDstNumber(srcToDst, srcToMappings, srcNumber = dstNumber, src = dstSrc)
    }

    var regexSeeds = Regex("""
        seeds:\s+(.*)(?:.|\n)*
    """.trimIndent())
    var regexMappingsHeading = Regex("""
        (\w+)-to-(\w+)\s*map:\s*((?:\d+\s*)+)
    """.trimIndent())
    var regexMappingsRow = Regex("""
        (\d+)\s+(\d+)\s+(\d+)\s+
    """.trimIndent())

    fun solvePuzzle2(input: List<String>): Int {
        return TODO("")
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day05-example1.txt,35",
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
            "/day05-example1.txt,0",
            "/day05-input1.txt,0"
        ]
    )
    fun testPuzzle2(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle2(input))
    }
}
