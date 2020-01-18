import java.io.File
fun prettyPrint(image :List<String>, width :Int, height :Int){
	var newImage = image.joinToString("")
		.replace("1", "x")
		.replace("0", " ")
		.chunked(width).chunked(height)
	newImage.forEach { it.forEach { println(it) } }
}
fun main(){
	var width = 25
	var height = 6
	val layers = File("resources/day8.txt").readText().chunked(1).chunked(width * height)
	val result = layers.minBy { layer -> layer.count{ it.contains("0")}}?.let {
			layer ->
		layer.count{it.contains("1")} * layer.count{ it.contains("2")}
	}?: -1
	println("Part 1 result: $result")

	val decodedImage = layers.foldRight(layers.last()){layer, next -> layer.zip(next).map{ (new, old) ->
		if( new == "2" ) old else new}}
	prettyPrint(decodedImage, width, height)

}