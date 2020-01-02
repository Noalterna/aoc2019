import Intcode.Amplifier
import Intcode.Computer
import java.io.File

fun getCopy(puzzle_input :List<Int>) = puzzle_input.toMutableList()

fun permute(sequence :List<Int>) :List<List<Int>> {
	if(sequence.size ==1) { return listOf(sequence) }
	val perms = mutableListOf<List<Int>>()
	val element = sequence[0]
	for ( perm in permute(sequence.drop(1))) {
		for(i in 0..perm.size){
			val newPerm = perm.toMutableList()
			newPerm.add(i, element)
			perms.add(newPerm)
		}
	}
	return perms
}

fun part1(sequence: List<Int>, puzzle_input: List<Int>) :Int{
	val amplifier_A :Amplifier
	val amplifier_B :Amplifier
	val amplifier_C :Amplifier
	val amplifier_D :Amplifier
	val amplifier_E :Amplifier

	amplifier_A = Amplifier(getCopy(puzzle_input), listOf(sequence[0], 0))
	amplifier_A.run()
	amplifier_B = Amplifier(getCopy(puzzle_input), listOf(sequence[1], amplifier_A.lastOutput))
	amplifier_B.run()
	amplifier_C = Amplifier(getCopy(puzzle_input), listOf(sequence[2], amplifier_B.lastOutput))
	amplifier_C.run()
	amplifier_D = Amplifier(getCopy(puzzle_input), listOf(sequence[3], amplifier_C.lastOutput))
	amplifier_D.run()
	amplifier_E = Amplifier(getCopy(puzzle_input), listOf(sequence[4], amplifier_D.lastOutput))
	amplifier_E.run()
	var output = amplifier_E.lastOutput
	return output
}
fun main() {
	val puzzle_input = File("resources/day7.txt").readText().split(',').map{it.toInt()}

	val puzzleExample = listOf<Int>(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
		1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0)

	val permutations = permute(mutableListOf(0,1,2,3,4))
	var outputs = mutableListOf<Int>()
	permutations.forEach {
		 outputs.add(part1(it, puzzle_input))
	}
	//outputs = part1(listOf(1,0,4,3,2), puzzleExample)
	println(outputs.max())
}