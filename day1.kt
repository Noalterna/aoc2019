import java.io.File
import kotlin.math.*

fun readFile(): MutableList<String>{
    val fileName = "d:/advent2019/day1.txt"
    val myList = mutableListOf<String>()
    File(fileName).useLines { lines -> myList.addAll(lines)}
   return myList
}

fun fuel(mass:Int): Double{
    val x = mass.toDouble()/3
    return floor(x) - 2
}

fun main(args: Array<String>){
    val massListEx = mutableListOf(12, 14, 1969, 100756)
    massListx.forEach{println(fuel(it))}

    val massList = readFile()
    val fuelList = mutableListOf<Double>()
    massList.forEach{ fuelList.add(fuel(it.toInt()))}
    fuelList.forEach{println(it)}

    println("Sum of all fuel " + fuelList.sum())
}
