import java.io.File

fun readFileToList(fileName :String) :MutableList<Int>{
    val content = File(fileName).readText().split(',').map{ it.toInt()}
    return content.toMutableList()
}

fun computer(intCodeProgram :MutableList<Int>){
    intCodeProgram.forEachIndexed{i , element ->
        if ( i % 4 == 0 && element == 99) {return}
        else if( i % 4 == 0 && element == 2) {
            val a = intCodeProgram.elementAt(intCodeProgram.elementAt(i+1))
            val b = intCodeProgram.elementAt(intCodeProgram.elementAt(i+2))
            val goal = intCodeProgram.elementAt(i+3)
            intCodeProgram[goal] = a*b
        }
        else if(i % 4 == 0 && element == 1) {
            val a = intCodeProgram.elementAt(intCodeProgram.elementAt(i+1))
            val b = intCodeProgram.elementAt(intCodeProgram.elementAt(i+2))
            val goal = intCodeProgram.elementAt(i+3)
            intCodeProgram[goal] = a+b
        }
    }
}

fun part2(fileName: String): List<Int>{
    var initial_program = readFileToList(fileName)
    for(noun in 0..99){
        for(verb in 0..99){
            initial_program = readFileToList(fileName)
            initial_program[1] = noun
            initial_program[2] = verb
            computer(initial_program)
            if(initial_program[0] == 19690720){
                return listOf(initial_program[1],initial_program[2])
            }
        }
    }
    return listOf(100,100)
}

fun main(){
    val fileName = "resources/day2.txt"
    val intCodeProgram: MutableList<Int> = readFileToList(fileName)

    /*val example = mutableListOf(1,1,1,4,99,5,6,0,99)
    computer(example)
    example.forEach{ println(it)}*/

    intCodeProgram[1] = 12
    intCodeProgram[2] = 2
    computer(intCodeProgram)
    println("Part 1: " + intCodeProgram[0])

    val result_part2= part2(fileName)
    result_part2.forEach{println(it)}
    println("Part 2 " + 100*result_part2[0] + result_part2[1])
}
