import kotlin.math.*
import java.io.File

class ResourceReader( val filePath :String){
    fun text() = File(filePath).readText()
    fun lines() = text().lines()
}

enum class Direction{
    U,D,L,R
}
data class Move(val direction: Direction, val distance :Int)
data class Point(val x:Int, val y:Int)
operator fun Point.plus(move :Move) :Point{
    return when(move.direction){
        Direction.D -> copy(y = y - move.distance)
        Direction.U -> copy(y = y + move.distance)
        Direction.L -> copy(x = x - move.distance)
        Direction.R -> copy(x = x + move.distance)
    }
}
fun Point.distance() :Int = abs(x) + abs(y)

fun part1(wirePaths :List<List<Move>>) :Int?{
    val corners = wirePaths.map {
        it.fold(mutableListOf(Point(0, 0))) { list, next ->
            list.apply { list.add(last() + next) }
        }.drop(1)
    }

    val path1 = corners[0]
    val path2 = corners[1]
    val distances = mutableListOf<Int>()
    for(i in path1.dropLast(1).indices) {
        for (j in path2.dropLast(1).indices) {
            val yMin :Int
            val yMax :Int
            val xMin :Int
            val xMax :Int
            val x :Int
            val y :Int
            if(!(path1[i].x == path1[i+1].x && path2[j].y == path2[j+1].y
                || path1[i].y == path1[i+1].y && path2[j].x == path2[j+1].x)){continue} //check if paths are vertical/horizontal
            if(path1[i].x == path1[i+1].x){
                yMin = min(path1[i].y, path1[i+1].y)
                yMax = max(path1[i].y, path1[i+1].y)
                xMin = min(path2[j].x,path2[j+1].x)
                xMax = max(path2[j].x, path2[j+1].x)
                x = path1[i].x
                y = path2[j].y
                if(x in xMin .. xMax && y in yMin .. yMax){
                    distances.add(Point(x,y).distance())
                }
            }
            else{
                xMin = min(path1[i].x, path1[i+1].x)
                xMax = max(path1[i].x, path1[i+1].x)
                yMin = min(path2[j].y,path2[j+1].y)
                yMax = max(path2[j].y, path2[j+1].y)
                x = path2[j].x
                y = path1[i].y
                if(x in xMin .. xMax && y in yMin .. yMax){
                    distances.add(Point(x,y).distance())
                }
            }
        }
    }
    return distances.minBy{it}

}

fun main(){
    //Example
    /*val example = ("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51" + '\n' + "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    println(example)

    val pathExample = example.split('\n')
        .map{it.split(',').map{ chars ->
                Move(Direction.valueOf(chars.take(1)),chars.drop(1).toInt())}
    }
    val part1Example = part1(pathExample)
    println(part1Example)
    */
    
    //Puzzle Input
    val wirePaths = ResourceReader("resources/day3.txt").lines().map{
        it.split(',').map{ chars ->
            Move(Direction.valueOf(chars.take(1)), chars.drop(1).toInt())}
    }
    println(part1(wirePaths))

}