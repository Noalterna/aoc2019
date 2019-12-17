import java.io.File
import Intcode.Computer

fun main(){
	val fileName = "resources/day5.txt"
	val puzzleInput = File(fileName).readText().split(',').map{ it.toInt()}.toMutableList()

	val computer = Computer(puzzleInput, 5)
	computer.run()
}