import java.io.File

val REGEX = Regex("""(\d+) (\w+)""")

fun calculateOre(fuelQuantity: Long, reactions: Map<String,Pair<Long,Map<String,Long>>>): Long {
	val products = mutableMapOf("FUEL" to fuelQuantity)
	while (true){
		val (chemical, quantity) = products.entries.find{ (chemical,quantity) -> chemical != "ORE" && quantity > 0 }?: break
		val (productQuantity, substratesAndQuantity) = checkNotNull(reactions[chemical]){"null key = $chemical"}
		val x = (quantity + productQuantity -1) / productQuantity
		products[chemical] = quantity - productQuantity * x
		for((substrate, quantityS) in substratesAndQuantity){
			products[substrate] = products.getOrElse(substrate) {0} + x * quantityS
		}
	}
	return products.getOrElse("ORE") { 0 }
}
fun calculateFuel(reactions: Map<String,Pair<Long,Map<String,Long>>>, maxOre :Long): Long {
	var result = maxOre/calculateOre(1,reactions)
	var tmp: Long? = null
	while (tmp == null || result < tmp - 1){
		var quantity = tmp?.let{ (result + it)/2 }?: result*2
		var ore = calculateOre(quantity, reactions)
		if( ore < maxOre ){
			result = quantity
		}
		else if(ore == maxOre){
			return quantity
		}
		else{
			tmp = quantity
		}
	}
	return result
}

fun main(){
	val input = File("resources/day14.txt").readLines()
	val reactions = input.associate { line ->
		val substrates = REGEX.findAll(line).map{ match ->
			val (quantity, chemical) = match.destructured
			chemical to quantity.toLong()
 		}.toMutableList()
		val (product, quantity) = substrates.removeAt(substrates.lastIndex)
		product to (quantity to substrates.toMap())
	}
	println("Part 1: " + calculateOre(1, reactions))
	println("Part 2: " + calculateFuel(reactions,1000000000000L))
}