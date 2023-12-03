package com.jordigarcial

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day02 {
    /**
     * --- Day 2: Cube Conundrum ---
     *
     * You're launched high into the atmosphere! The apex of your trajectory just barely reaches the surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's quite cold, but you don't see much snow. An Elf runs over to greet you.
     *
     * The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't get many visitors up here; would you like to play a game in the meantime?
     *
     * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.
     *
     * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.
     *
     * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
     *
     * For example, the record of a few games might look like this:
     *
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     *
     * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
     *
     * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
     *
     * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
     *
     * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     *
     * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
     *
     * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
     *
     * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
     *
     * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
     */
    fun solvePuzzle1(input: List<String>): Int = input
        .mapNotNull { inputLine -> parseValidGame(inputLine) }
        .sumOf { game -> game.id }

    data class Game(val id: Int, val reveals: List<Map<String, Int>>)

    val regex = """Game (\d+): (.*)""".toRegex()

    private fun parseValidGame(inputLine: String) : Game? {
        val maxColorToCounts = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        // E.g. "Game 69: 8 blue, 1 green, 4 red; 3 red, 11 blue, 9 green; 12 blue, 10 green; 1 red, 15 blue, 7 green"
        println("Parsing game $inputLine")

        val matchResult = regex.matchEntire(inputLine)!!

        // E.g. "69"
        val id = matchResult.groups[1]!!.value.toInt()

        // E.g. "8 blue, 1 green, 4 red; 3 red, 11 blue, 9 green; 12 blue, 10 green; 1 red, 15 blue, 7 green"
        val gameNotes = matchResult.groups[2]!!.value

        val reveals = mutableListOf<Map<String, Int>>()

        // E.g. "8 blue, 1 green, 4 red"
        for (gameSet in gameNotes.split(';')) {
            val countToColorMap = mutableMapOf<String, Int>()

            // E.g. "8 blue"
            for (countAndColor in gameSet.split(',')) {
                val value = countAndColor.trim().split(' ')
                // E.g. "8"
                val count = value[0].toInt()
                // E.g. "blue"
                val color = value[1]

                // Ensure valid game
                if (count > maxColorToCounts[color]!!) {
                    println("Game not null!\n")
                    return null
                }

                countToColorMap[color] = (countToColorMap[color] ?: 0) + count
            }

            reveals.add(countToColorMap)
        }

        return Game(id, reveals)
    }

    fun solvePuzzle2(input: List<String>): Nothing = TODO()

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day02-example1.txt,8",
            "/day02-input1.txt,0"
        ]
    )
    fun testPuzzle1(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle1(input))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "/day01-example2.txt,281",
            "/day01-input1.txt,54649"
        ]
    )
    fun testPuzzle2(inputFile: String, result: String) {
        val input = IOUtils.readLines(javaClass.getResourceAsStream(inputFile), "UTF-8") as List<String>
        assertEquals(Integer.parseInt(result), solvePuzzle2(input))
    }
}
