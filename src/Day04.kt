const val ROLLS_OF_PAPER = '@'

fun main() {

    fun parse(input: String) = CharGrid(input)

    fun part1(grid: CharGrid) = grid.findMovableRolls().count()

    fun part2(grid: CharGrid): Long {
        var total = 0L
        while (true) {
            val movableRolls = grid.findMovableRolls()
            if (movableRolls.count() == 0) {
                return total
            }
            movableRolls.forEach { roll -> grid.set(roll, 'x') }

            total += movableRolls.count()
        }
    }

    val input = parse(readInput("Day04"))
    part1(input).println()
    part2(input).println()
}

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val NORTH_WEST = Vector2d(-1, -1)
        val NORTH = Vector2d(0, -1)
        val NORTH_EAST = Vector2d(1, -1)
        val EAST = Vector2d(1, 0)
        val SOUTH_EAST = Vector2d(1, 1)
        val SOUTH = Vector2d(0, 1)
        val SOUTH_WEST = Vector2d(-1, 1)
        val WEST = Vector2d(-1, 0)

        val ORTHOGONAL_ADJACENT = listOf(
            NORTH,
            EAST,
            SOUTH,
            WEST
        )
        val DIAGONAL_ADJACENT = listOf(
            NORTH_EAST,
            SOUTH_EAST,
            SOUTH_WEST,
            NORTH_WEST
        )

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT
    }
}

data class Point2d(
    val x: Int,
    val y: Int
) {
    fun neighbours(
        directions: List<Vector2d>
    ) = directions.map { this + it }

    operator fun plus(v: Vector2d) = Point2d(x + v.x, y + v.y)
}

data class Dimension(
    val width: Int,
    val height: Int
) {
    fun contains(p: Point2d): Boolean = p.x in 0..<width && p.y in 0..<height
}

data class CharGrid(
    val grid: List<MutableList<Char>>
) {
    constructor(input: String) : this(input.replace("\r", "").lines().map { it.toMutableList() }.toMutableList())

    fun dimension(): Dimension = Dimension(grid[0].size, grid.size)
    fun contains(point: Point2d): Boolean = dimension().contains(point)

    fun findAll(value: Char): List<Point2d> = grid.flatMapIndexed { row, line ->
        line.indices.filter { column -> line[column] == value }
            .map { column -> Point2d(column, row) }
    }

    fun at(row: Int, column: Int) = grid[row][column]
    fun at(point: Point2d) = at(point.y, point.x)

    fun set(point: Point2d, value: Char) {
        grid[point.y][point.x] = value
    }

    fun findMovableRolls() = findAll(ROLLS_OF_PAPER).filter { point ->
        point.neighbours(Vector2d.SURROUNDING)
            .count { contains(it) && at(it) == ROLLS_OF_PAPER } < 4
    }
}