import java.io.File
import kotlin.math.atan2
import kotlin.math.PI
import kotlin.math.sqrt

data class Asteroid(val x: Int, val y: Int) {
	fun angle(target: Asteroid) = atan2((target.x - x).toDouble(), (target.y - y).toDouble())
	fun distance(other: Asteroid): Double {
		return sqrt(((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y)).toDouble())
	}
}
fun countVisible(asteroid: Asteroid, asteroids: List<Asteroid>) =
	asteroids.filterNot{ it == asteroid}.map{it -> it.angle(asteroid)}.distinct().size

fun part2(station: Pair<Asteroid, Int>?, asteroids: List<Asteroid>): Int{
	val sortedAsteroidsToVaporize = asteroids.filterNot{ it == station?.first}
		.groupBy { station!!.first.angle(it) + PI}
		.map{ e -> e.key to e.value.sortedBy { asteroid -> station!!.first.distance(asteroid) }}
		.sortedByDescending { it.first }
	println(sortedAsteroidsToVaporize)

	var asteroid = Asteroid(0,0)
	for(i in 0..199) {
		val tmpList = sortedAsteroidsToVaporize[i%sortedAsteroidsToVaporize.size].second.toMutableList()
		if (tmpList.isNotEmpty()){
			asteroid = tmpList.elementAt(0)
			tmpList.removeAt(0)
		}
	}
	return asteroid.x*100 + asteroid.y

}
fun main() {
	val asteroidMap = File("resources/day10.txt").readLines().map{
		it.chunked(1)}
	val asteroids by lazy {
		asteroidMap.indices.flatMap { y -> asteroidMap[y].indices.map { x -> Asteroid(x, y) } }
			.filter { (x, y) -> asteroidMap[y][x] == "#" }
	}
	val station by lazy {
		asteroids.map{it to countVisible(it, asteroids)}.maxBy{ it.second }
	}

	println("Part 1 result (number of asteroids): " + station?.second)
	println("Part 2 result: " + part2(station, asteroids))


}