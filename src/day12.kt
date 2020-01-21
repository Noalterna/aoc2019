import io.github.ephemient.aoc2019.Day12
import kotlinx.coroutines.yield
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

typealias Positions = MutableList<MutableList<Int>>
typealias Velocities = MutableList<MutableList<Int>>


fun simulate(moonPositions: List<MutableList<Int>>, steps: Int = 0)
		: Pair<Positions, Velocities> {
	val velocities= MutableList(moonPositions.size){ MutableList(moonPositions[0].size){0} }
	val positions by lazy{ moonPositions.map{ it.map{ it }.toMutableList()}.toMutableList()}

	for(step in 0 until steps) {
		for ((moonId, point) in positions.withIndex()) {
			for (j in 0..2) {
				velocities[moonId][j] += positions.sumBy { it -> (it[j] - point[j]).sign}
			}
		}
		for (moonId in positions.indices) {
			for (j in 0..2) {
				positions[moonId][j] += velocities[moonId][j]
			}
		}
	}
	return Pair(positions, velocities)
}

fun lcm(x: Long, y: Long): Long{
	var a = x
	var b= y
	while (a != 0L){
		a = (b % a).also { b = a }
	}
	return x / b * y
}

fun countTotalEnergy(motion: Pair<Positions, Velocities>): Int{
	val potentialEnergy = motion.first.map { it.sumBy { abs(it) } }
	val kineticEnergy = motion.second.map { it.sumBy { abs(it) } }
	return potentialEnergy.zip(kineticEnergy) { pE, kE -> pE*kE}.sumBy {it}
}

fun main() {
	val input = File("resources/day12.txt").readLines()
	val moonsPositions = input.map{ Regex("""-?\d+""").findAll(it)
		.map{ match -> match.value.toInt()}.toMutableList()
	}
	val motion = simulate(moonsPositions, 10)
	println("Part 1 result: " + countTotalEnergy(motion))

}