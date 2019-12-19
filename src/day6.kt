import java.io.File
fun getPaths( orbitMap :Map<String, List<String>>, current :String, path :List<String> = emptyList() ):List<String> {
	return if(!orbitMap.containsKey(current)) path
	else {
		val next = orbitMap.getValue(current).first().toString()
		getPaths(orbitMap, next, path + current)
	}
}
fun main() {
	val fileName = "resources/day6.txt"
	val orbitMap = File(fileName).readLines()
		.map{ it.split(')') }
		.groupBy( { it.last() }, { it.first() } )
	val pathSize = orbitMap.keys
		.map{ getPaths(orbitMap, it) }
		.flatten().size
	println("Part 1: " + pathSize)

	val pathFromYou = getPaths(orbitMap, "YOU").drop(1)
	val pathFromSan = getPaths(orbitMap,"SAN").drop(1)
	val pathYouToSan = pathFromYou + pathFromSan - (pathFromSan intersect pathFromYou)
	println("Part 2: " + pathYouToSan.size)




}