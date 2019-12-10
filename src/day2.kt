import java.io.File

fun readFileToList(fileName :String) :MutableList<Int>{
    val content = File(fileName).readText()
    val myList :List<Int> = content.split(',').map{ it.toInt()}
    return myList.toMutableList()
}

fun computer(intCodeProgram :MutableList<Int>){
    intCodeProgram.forEachIndexed{i , element ->
        if ( i % 4 == 0 && element == 99) {return}
        else if( i % 4 == 0 && element == 2) {
            var a = intCodeProgram.elementAt(intCodeProgram.elementAt(i+1))
            var b = intCodeProgram.elementAt(intCodeProgram.elementAt(i+2))
            var goal = intCodeProgram.elementAt(i+3)
            intCodeProgram[goal] = a*b
        }
        else if(i % 4 == 0 && element == 1) {
            var a = intCodeProgram.elementAt(intCodeProgram.elementAt(i+1))
            var b = intCodeProgram.elementAt(intCodeProgram.elementAt(i+2))
            var goal = intCodeProgram.elementAt(i+3)
            intCodeProgram[goal] = a+b
        }
    }
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
    intCodeProgram.forEach{println(it)}
    println(intCodeProgram[0])
}
