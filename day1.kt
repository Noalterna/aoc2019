import java.io.File
import kotlin.math.*

fun readFile(): MutableList<String>{
    val fileName = "./day1.txt"
    val myList = mutableListOf<String>()
    File(fileName).useLines { lines -> myList.addAll(lines)}
   return myList
}

fun fuel(mass:Double): Double{
    val x = mass/3
    return floor(x) - 2
}

fun main(args: Array<String>){
    //val massListEx = mutableListOf(12.0, 14.0, 1969.0, 100756.0)
    //massListx.forEach{println(fuel(it))}

    val massList = readFile()
    val fuelList = mutableListOf<Double>(14.0)
    massList.forEach{ fuelList.add(fuel(it.toDouble()))}
   println("Sum of fuel " + fuelList.sum())

    var sum = 0.0
    massList.forEach {
        var current = it.toDouble()
        while(fuel(current) > 0) {
            sum = sum + fuel(current)
            current = fuel(current)
        }
    }
    println("Sum with additional fuel " + sum)
}
