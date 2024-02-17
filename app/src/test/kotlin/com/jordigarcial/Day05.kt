package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day05 {
    fun solvePuzzle1(input: List<String>): Long {
        val seeds = regexSeeds.matchEntire(input[0])!!.groupValues[1]
            .split(" ")
            .map { it.toLong() }
            .toSet()

        val srcToDst = mutableMapOf<String, String>()
        val srcToMappings = mutableMapOf<String, MutableMap<Long, Long>>()

        var currentSrc: String? = null
        for (i in 1 until input.size) {
            val inputLine = input[i]
            when {
                inputLine.isBlank() -> {
                    currentSrc = null
                }
                currentSrc == null -> {
                    regexMappingsHeading.matchEntire(input[i])!!.let { matchResult ->
                        val src = matchResult.groupValues[1]
                        val dst = matchResult.groupValues[2]
                        srcToDst += src to dst
                        currentSrc = src
                    }
                }
                else -> {
                    regexMappingsRow.matchEntire(input[i])!!.let { matchResult ->
                        val dstId = matchResult.groupValues[1].toLong()
                        val srcId = matchResult.groupValues[2].toLong()
                        val range = matchResult.groupValues[3].toLong()
                        for (j in 0 until range) {
                            val mappings = srcToMappings.computeIfAbsent(currentSrc!!) { mutableMapOf() }
                            mappings += (srcId + j) to (dstId + j)
                        }
                    }
                }
            }
        }

        return seeds.minOf { seed ->
            evaluateLocationDstNumber(srcToDst, srcToMappings, srcNumber = seed).also {
                println("Evaluating $seed: location $it")
            }
        }
    }

    private fun evaluateLocationDstNumber(
        srcToDst: MutableMap<String, String>,
        srcToMappings: Map<String, Map<Long, Long>>,
        srcNumber: Long,
        src: String = "seed"
    ): Long {
        println(srcNumber)
        println(src)

        if (src == "location") {
            return srcNumber
        }

        val dstNumber = srcToMappings[src]!![srcNumber] ?: srcNumber
        val dstSrc = srcToDst[src]!!
        return evaluateLocationDstNumber(srcToDst, srcToMappings, srcNumber = dstNumber, src = dstSrc)
    }

    var regexSeeds = Regex("""seeds:\s+(.*)""")
    var regexMappingsHeading = Regex("""(\w+)-to-(\w+)\s*map:\s*""")
    var regexMappingsRow = Regex("""(\d+)\s+(\d+)\s+(\d+)""")

    fun solvePuzzle2(input: List<String>): Long {
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
        assertEquals(result.toLong(), solvePuzzle1(input))
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
        assertEquals(result.toLong(), solvePuzzle2(input))
    }
}
