# advent-of-code-2023 (kotlin)

This project attempts to solve the daily puzzles over at https://adventofcode.com/2023/ using `Gradle` (build manager), `Kotlin` (language) and `JUnit5` (test engine).

**Where are the puzzles?**

The puzzles are implemented as JUnit 5 test classes under `./app/src`. There's a test class for each day (e.g. `Day01.kt`). 


Each test class usually has two tests `testPuzzle1()` `testPuzzle2()`, which cover the first and second puzzle parts respectively.

The tests are parameterized and when possible test at least two cases: one for the example puzzle input provided by AoC (`exampleInput:exampleSolution`), and another one with the actual puzzle input and the valid solution (`actualInput:solution`).

**Where can I check the solution to each puzzle?**

As explained above, solutions are in the test cases. For example, the test case below shows that for `Day03-part2` the solution is `87287096`. 

```kotlin
    // Day03.kt

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
```

**How can I run the tests?**

You can run the tests with:

```shell
./gradlew test
```
