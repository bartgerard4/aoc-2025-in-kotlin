val RANGE_PATTERN = "(\\d+)-(\\d+)".toRegex()
val REPEATED_ONCE = "(\\d+)\\1".toRegex()
val REPEATED_AT_LEAST_TWICE = "(\\d+)\\1+".toRegex()

private fun String.toLongRanges() = RANGE_PATTERN.findAll(this)
    .map { it.destructured }
    .map { (start, end) -> start.toLong()..end.toLong() }
    .toList()

private fun List<LongRange>.filterByPattern(pattern: Regex) = flatMap { id ->
    id.filter { it.toString().matches(pattern) }
}

fun main() {

    fun parse(input: String) = input.toLongRanges()

    fun part1(input: List<LongRange>) = input.filterByPattern(REPEATED_ONCE).sum()

    fun part2(input: List<LongRange>) = input.filterByPattern(REPEATED_AT_LEAST_TWICE).sum()


    // Read the input from the `src/Day01.txt` file.
    val input = parse(readInput("Day02"))
    part1(input).println()
    part2(input).println()
}

