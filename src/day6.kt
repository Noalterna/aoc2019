import java.io.File
fun getPath( orbitMap :Map<String, List<String>>, current :String, path :List<String> = emptyList() ):List<String> {
	return if(!orbitMap.containsKey(current)) path
	else {
		val next = orbitMap.getValue(current).first().toString()
		getPath(orbitMap, next, path + current)
	}
}
fun main() {
	val fileName = "resources/day6.txt"
	val orbitMap = File(fileName).readLines()
		.map{ it.split(')') }
		.groupBy( { it.last() }, { it.first() } )
	val pathSize = orbitMap.keys
		.map{ getPath(orbitMap, it) }
		.flatten().size
	println("Part 1: " + pathSize)

	//Example
	val example = listOf<String>("COM)B","B)C","C)D","D)E","E)F","B)G", "G)H","D)I","E)J","J)K","K)L")
	val inne = example.map{ it.split(')') }
		.groupBy( { it.last() }, { it.first() } )
	val x = inne.keys.map{ getPath(inne, it)}.flatten().size



}