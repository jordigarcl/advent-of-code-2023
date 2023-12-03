package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.regex.Pattern
import kotlin.math.min

/**
 * --- Day 1: Trebuchet?! ---
 *
 * Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.
 *
 * You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.
 *
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 *
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").
 *
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.
 *
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 *
 * For example:
 *
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 *
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 *
 * Consider your entire calibration document. What is the sum of all of the calibration values?
 */
class Day01 {
    fun solvePuzzle1(input: List<String>): Int {
        var finalResult = 0

        // Process each input line
        for (inputLine in input) {
            var firstDigit: Char? = null
            var lastDigit: Char? = null
            // Process each input line char
            for (inputLineChar in inputLine) {
                // Check char is a digit
                if (inputLineChar.isDigit()) {
                    // If is the first digit ever seen, remember it
                    if (firstDigit == null) firstDigit = inputLineChar
                    // Remember it as last digit seen (so far)
                    lastDigit = inputLineChar
                }
            }
            val inputLineResult = (firstDigit!!.digitToInt() * 10) + lastDigit!!.digitToInt()
            finalResult += inputLineResult
        }

        return finalResult
    }

    fun solvePuzzle2(input: List<String>): Int {
        var finalResult = 0

        // Process each inputLine
        for (inputLine in input) {
            println("Parsing: $inputLine")
            var firstDigitSeen: Int? = null
            var lastDigitSeen: Int? = null
            var left = 0
            var right = 1
            val maxWindowSize = min(5, inputLine.length)

            // Iterate inputLine on a sliding window
            while (right <= inputLine.length) {
                val slidingWindow = inputLine.subSequence(left, right)
                // Attempt to parse end of sliding window
                when {
                    slidingWindow.endsWith("one") || slidingWindow.endsWith('1') -> 1
                    slidingWindow.endsWith("two") || slidingWindow.endsWith('2') -> 2
                    slidingWindow.endsWith("three") || slidingWindow.endsWith('3') -> 3
                    slidingWindow.endsWith("four") || slidingWindow.endsWith('4') -> 4
                    slidingWindow.endsWith("five") || slidingWindow.endsWith('5') -> 5
                    slidingWindow.endsWith("six") || slidingWindow.endsWith('6') -> 6
                    slidingWindow.endsWith("seven") || slidingWindow.endsWith('7') -> 7
                    slidingWindow.endsWith("eight") || slidingWindow.endsWith('8') -> 8
                    slidingWindow.endsWith("nine") || slidingWindow.endsWith('9') -> 9
                    else -> null
                }?.let { parsedDigit ->
                    println("Parsed: $parsedDigit")
                    // If is the first digit ever seen, remember it
                    firstDigitSeen = firstDigitSeen ?: parsedDigit
                    // Remember it as last digit seen so far
                    lastDigitSeen = parsedDigit
                    // There was a match so we advance the window
                    left += 1
                    right += 1
                } ?: run {
                    // There was no match so we try to expand window first
                    if (right - left < maxWindowSize) {
                        right += 1
                    }
                    // If window is already fully expanded then we advance it
                    else {
                        left += 1
                        right += 1
                    }
                }
            }


            val inputLineResult = (firstDigitSeen!! * 10) + lastDigitSeen!!
            finalResult += inputLineResult
        }

        return finalResult
    }

    @ParameterizedTest
    @CsvSource(value = [
        "/day01-example1.txt,142",
        "/day01-input1.txt,54081",
    ])
    fun testPuzzle1(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle1(input))
    }

    @ParameterizedTest
    @CsvSource(value = [
        "/day01-example2.txt,281",
        "/day01-input1.txt,54649"
    ])
    fun testPuzzle2(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle2(input))
    }
}
