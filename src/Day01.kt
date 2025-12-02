import kotlin.math.absoluteValue

const val START = 50
const val DIAL_NUMBERS = 100

private fun Int.rotate(clicks: Int) = (this + clicks).mod(DIAL_NUMBERS)

fun main() {

    fun parse(input: String) = input.lines()
        .map { (if (it[0] == 'L') -1 else 1) * it.substring(1).toInt() }
        .toList()

    fun part1(input: List<Int>) = input.scan(START) { dialPosition, clicks -> dialPosition.rotate(clicks) }
        .count { it == 0 }

    fun part2(input: List<Int>) = input.fold(START to 0L) { (dialPosition, zeroCount), clicks ->
        val fullCrossings = ((dialPosition + clicks) / DIAL_NUMBERS).absoluteValue
        val zeroCrossing = if (dialPosition != 0 && clicks <= -dialPosition) 1L else 0L

        dialPosition.rotate(clicks) to zeroCount + fullCrossings + zeroCrossing
    }
        .second

    val input = parse(readInput("Day01"))
    part1(input).println()
    part2(input).println()
}
