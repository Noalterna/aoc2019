import java.io.File
import kotlin.math.abs
fun FFT(input: List<Int>, phasesNumber: Int): List<Int> {
	val signal = input.toMutableList()
	repeat(phasesNumber) {
		for (i in signal.indices) {
			var pattern = true;
			signal[i] = abs((i..signal.size step 2 * i + 2).sumBy { it ->
				(it until minOf(it + i + 1, signal.size)).sumBy { signal[it] }
					.let { if (pattern) it else -it }
					.also { pattern = !pattern }
			}) % 10
		}
	}
	return signal
}
fun part2(input: List<Int>, phasesNumber: Int) : MutableList<Int>{
	val offset = input.take(7).joinToString("").toInt()
	val newSize = 10000 * input.size
	check(offset < newSize && newSize <= 2 * offset)
	val value = (offset until newSize).map{ input[it % input.size] }.toMutableList()
	repeat(phasesNumber){
		value.indices.reversed().fold(0){ acc, i ->
			(abs(acc + value[i]) % 10).also{ value[i] = it}
		}
	}
	return value
}
fun main(){
	val input = File("resources/day16.txt").readText().map{ Character.digit(it,10)}
	val decodedSignal = FFT(input, 100)
	println("Part 1: " + decodedSignal.take(8).joinToString(""))

	println("Part 2: " + part2(input,100).take(8).joinToString(""))


}