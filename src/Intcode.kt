package Intcode

class Computer(var code :MutableList<Int>, val input_value :Int){
	var instructionPointer = 0

	fun executeInstruction(opcode :Int){
		val instruction = opcode.rem(100)
		val c_Mode = (opcode/100).rem(10)
		val b_Mode = (opcode/1000).rem(10)
		val a_Mode = (opcode/10000).rem(10)
		var param1 :Int = 0
		var param2 :Int = 0

		if (instructionPointer < code.size - 1 ){
			if (c_Mode == 1) { param1 = instructionPointer + 1 }
			else { param1 = code[instructionPointer + 1] }
		}
		if (instructionPointer < code.size -2) {
			if (b_Mode == 1) { param2 = instructionPointer + 2 }
			else { param2 = code[instructionPointer + 2] }
		}

		when(instruction){
			1 -> add(param1, param2)
			2 -> multiply(param1, param2)
			3 -> input(param1)
			4 -> output(param1)
			5 -> jumpIfTrue(param1, param2)
			6 -> jumpIfFalse(param1, param2)
			7 -> lessThan(param1, param2)
			8 -> equals(param1, param2)
			99 -> halt()
			else -> {println("wrong opcode $opcode"); instructionPointer += 1}
		}
	}
	fun add(param1 :Int, param2 :Int){
		val param3 = code[instructionPointer+3]
		code[param3] = code[param1] + code[param2]
		instructionPointer += 4
	}
	fun multiply(param1 :Int, param2 :Int){
		val param3 = code[instructionPointer+3]
		code[param3] = code[param1] * code[param2]
		instructionPointer += 4
	}
	fun input(param1 :Int){
		code[param1] = input_value
		instructionPointer += 2
	}
	fun output(param1 :Int){
		println(code[param1])
		instructionPointer += 2
	}
	fun jumpIfTrue(param1: Int, param2: Int) {
		if ( code[param1] != 0) { instructionPointer = code[param2] }
		else { instructionPointer += 3}
	}
	fun jumpIfFalse(param1: Int, param2: Int) {
		if ( code[param1] == 0){ instructionPointer = code[param2] }
		else { instructionPointer += 3}
	}
	fun lessThan(param1: Int, param2: Int) {
		val param3 = code[instructionPointer+3]
		if (code[param1] < code[param2]) { code[param3] = 1 }
		else { code[param3] = 0 }
		instructionPointer += 4
	}
	fun equals(param1 :Int, param2: Int) {
		val param3 = code[instructionPointer+3]
		if (code[param1] == code[param2]) { code[param3] = 1 }
		else { code[param3] = 0 }
		instructionPointer += 4
	}
	fun halt(){
		instructionPointer = code.size
	}
	fun run(){
		while(instructionPointer < code.size) {
			val opcode = code[instructionPointer]
			executeInstruction(opcode)
		}
	}
}