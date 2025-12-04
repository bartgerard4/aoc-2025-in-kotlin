fun main() {

    fun parse(input: String) = input.lines().map { line -> line.map { c -> c.digitToInt() } }

    fun maxJoltage(numbers: List<Int>, length: Int): String {
        val max = numbers.subList(0, numbers.size - length + 1).max()

        if (length == 1) {
            return max.toString()
        }

        val indexOfFirstMax = numbers.indexOfFirst { it == max }

        return max.toString() + maxJoltage(numbers.subList(indexOfFirstMax + 1, numbers.size), length - 1)
    }

    fun part1(input: List<List<Int>>) = input.sumOf { maxJoltage(it, 2).toLong() }

    fun part2(input: List<List<Int>>) = input.sumOf { maxJoltage(it, 12).toLong() }

    val input = parse(readInput("Day03"))
    part1(input).println()
    part2(input).println()
}

