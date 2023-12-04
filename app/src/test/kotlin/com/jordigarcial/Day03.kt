package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.awt.geom.Line2D
import java.awt.geom.Point2D

class Day03 {

    /**
     * --- Day 3: Gear Ratios ---
     *
     * You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.
     *
     * It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
     *
     * "Aaah!"
     *
     * You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
     *
     * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
     *
     * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
     *
     * Here is an example engine schematic:
     *
     * 467..114..
     *
     * ...*......
     *
     * ..35..633.
     *
     * ......#...
     *
     * 617*......
     *
     * .....+.58.
     *
     * ..592.....
     *
     * ......755.
     *
     * ...$.*....
     *
     * .664.598..
     *
     * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
     *
     * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
     */
    fun solvePuzzle1(input: List<String>): Int {
        val foundNumbers = mutableListOf<Pair<Int, Line2D>>()
        val foundSymbols = mutableListOf<Point2D>()

        // E.g. "467..114.."
        for ((y, inputLine) in input.withIndex()) {
            // E.g. "467"
            regexNumber.findAll(inputLine).forEach { matchResult ->
                val number = matchResult.value.toInt()
                val coords = Line2D.Double(
                    Point2D.Double(matchResult.range.first.toDouble(), y.toDouble()),
                    Point2D.Double(matchResult.range.last.toDouble(), y.toDouble())
                )
                println("Found $number at ${coords.stringify()}")
                foundNumbers += number to coords
            }

            // E.g. "*"
            regexSymbols.findAll(inputLine).forEach { matchResult ->
                val coord = Point2D.Double(matchResult.range.first.toDouble(), y.toDouble())
                println("Found symbol at ${coord.stringify()}")
                foundSymbols +=  coord
            }
        }

        val matchingNumbers = mutableListOf<Int>()

        for (number in foundNumbers) {
            for (symbol in foundSymbols) {
                if (symbol.isAdjacentToLine(number)) {
                    println("Number ${number.first} on ${number.second.stringify()} is adjacent to symbol on ${symbol.stringify()}")
                    matchingNumbers.add(number.first)
                }
            }
        }
        return matchingNumbers.sum()
    }

    val regexNumber = Regex("""(\d+)+""")
    val regexSymbols = Regex("""[^0-9.\n]""")
    fun Point2D.stringify() = "(${x.toInt()},${y.toInt()})"
    fun Line2D.stringify() = "[${p1.stringify()},${p2.stringify()}]"

    fun Point2D.isAdjacentToLine(number: Pair<Int, Line2D>): Boolean {
        val distance = number.second.ptSegDist(this)
        return distance <= 1.4142135623730951;
    }

    /**
     * --- Part Two ---
     *
     * The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.
     *
     * You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.
     *
     * Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.
     *
     * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
     *
     * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
     *
     * Consider the same engine schematic again:
     *
     * 467..114..
     *
     * ...*......
     *
     * ..35..633.
     *
     * ......#...
     *
     * 617*......
     *
     * .....+.58.
     *
     * ..592.....
     *
     * ......755.
     *
     * ...$.*....
     *
     * .664.598..
     *
     * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
     *
     * What is the sum of all of the gear ratios in your engine schematic?
     */
    fun solvePuzzle2(input: List<String>) : Int {
        val foundNumbers = mutableListOf<Pair<Int, Line2D>>()
        val foundSymbols = mutableListOf<Point2D>()

        // E.g. "467..114.."
        for ((y, inputLine) in input.withIndex()) {
            // E.g. "467"
            regexNumber.findAll(inputLine).forEach { matchResult ->
                val number = matchResult.value.toInt()
                val coords = Line2D.Double(
                    Point2D.Double(matchResult.range.first.toDouble(), y.toDouble()),
                    Point2D.Double(matchResult.range.last.toDouble(), y.toDouble())
                )
                println("Found $number at ${coords.stringify()}")
                foundNumbers += number to coords
            }

            // E.g. "*"
            regexSymbols.findAll(inputLine).forEach { matchResult ->
                val coord = Point2D.Double(matchResult.range.first.toDouble(), y.toDouble())
                println("Found symbol at ${coord.stringify()}")
                foundSymbols +=  coord
            }
        }

        var gearRatiosSum = 0
        for (symbol in foundSymbols) {
            val gearNumbers = mutableListOf<Int>()
            for (number in foundNumbers) {
                if (symbol.isAdjacentToLine(number)) {
                    println("Number ${number.first} on ${number.second.stringify()} is adjacent to symbol on ${symbol.stringify()}")
                    gearNumbers += number.first
                }
            }
            if (gearNumbers.size == 2) {
                println("Gear detected: ${symbol.stringify()}")
                gearRatiosSum += gearNumbers[0] * gearNumbers[1]
            }
        }
        return gearRatiosSum
    }


    @ParameterizedTest
    @CsvSource(
        value = [
            "/day03-example1.txt,4361",
            "/day03-input1.txt,535351"
        ]
    )
    fun testPuzzle1(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle1(input))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day03-example1.txt,467835",
            "/day03-input1.txt,87287096"
        ]
    )
    fun testPuzzle2(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle2(input))
    }
}
