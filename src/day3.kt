import kotlin.math.*
import java.io.File

class ResourceReader( val filePath :String){
    fun text() = File(filePath).readText()
    fun lines() = text().lines()
}

enum class Direction{U,D,L,R}
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

fun getCorners(wirePaths :List<List<Move>>): List<List<Point>>{
    return wirePaths.map {
        it.fold(mutableListOf(Point(0, 0))) { list, next ->
            list.apply { list.add(last() + next) }
        }.drop(1)}
}
fun getIntersections(path1 :List<Point>, path2 :List<Point> ):List<Point>{
    val intersections = mutableListOf<Point>()
    for (i in path1.dropLast(1).indices) {
        for (j in path2.dropLast(1).indices) {
            val yMin: Int
            val yMax: Int
            val xMin: Int
            val xMax: Int
            val x: Int
            val y: Int
            if (path1[i].x == path1[i + 1].x && path2[j].y == path2[j + 1].y) {
                yMin = min(path1[i].y, path1[i + 1].y)
                yMax = max(path1[i].y, path1[i + 1].y)
                xMin = min(path2[j].x, path2[j + 1].x)
                xMax = max(path2[j].x, path2[j + 1].x)
                x = path1[i].x
                y = path2[j].y
                if (x in xMin..xMax && y in yMin..yMax) {
                    intersections.add(Point(x, y))
                }
            } else if (path1[i].y == path1[i + 1].y && path2[j].x == path2[j + 1].x) {
                xMin = min(path1[i].x, path1[i + 1].x)
                xMax = max(path1[i].x, path1[i + 1].x)
                yMin = min(path2[j].y, path2[j + 1].y)
                yMax = max(path2[j].y, path2[j + 1].y)
                x = path2[j].x
                y = path1[i].y
                if (x in xMin..xMax && y in yMin..yMax) {
                    intersections.add(Point(x, y))
                }
            }
        }
    }
    return intersections
}

fun part1(wirePaths :List<List<Move>>) :Int?{
    val corners = getCorners(wirePaths)
    val distances = mutableListOf<Int>()
    getIntersections(corners[0],corners[1]).forEach{ distances.add(it.distance())}
    return distances.min()
}

fun getSteps(path :List<Point>, intersection :Point):Int{
    var steps = 0
    var difference :Int= abs(path[0].x + path[0].y)
    for(i in path.dropLast(1).indices){
        val point = path[i]
        val point_next = path[i+1]
        steps += difference
        if(intersection.x in point.x .. point_next.x && point.x == point_next.x
            || intersection.y in point.y .. point_next.y && point.y == point_next.y){
            steps+= abs((point.x + point.y) - (intersection.x + intersection.y))
            return steps
        }
        difference = abs((point.x + point.y) - (point_next.x + point_next.y))
    }
    return steps
}

fun part2(wirePaths: List<List<Move>>) :Int?{
    val corners = getCorners(wirePaths)
    val path1 = corners[0]
    val path2 = corners[1]
    val intersections = getIntersections(path1, path2)
    val steps = mutableListOf<Int>()
    intersections.forEach{
        val sum = getSteps(path1, it) + getSteps(path2, it )
        steps.add(sum)
    }
    return steps.min()
}
fun main(){
    //Example
/*
    val example = ("R75,D30,R83,U83,L12,D49,R71,U7,L72" + '\n' + "U62,R66,U55,R34,D71,R55,D58,R83")
    val pathExample = example.split('\n')
        .map{it.split(',').map{ chars ->
                Move(Direction.valueOf(chars.take(1)),chars.drop(1).toInt())}}
    val part1Example = part1(pathExample)
    println(part1Example)
*/

    //Puzzle Input
    val wirePaths = ResourceReader("resources/day3.txt").lines().map{
        it.split(',').map{ chars ->
            Move(Direction.valueOf(chars.take(1)), chars.drop(1).toInt())}
    }
    println("Part 1: " + part1(wirePaths))
    println("Part 2: " + part2(wirePaths))

}