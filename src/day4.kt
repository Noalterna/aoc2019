fun isDouble(password: String) :Boolean{
	var count = 0
	val counts = mutableListOf<Int>()
	for(i in password.dropLast(1).indices){
		val currentDigit = password[i]
		val nextDigit = password[i+1]
		if(currentDigit == nextDigit){count++}
		else{
			counts.add(count)
			count = 0 }
	}
	counts.add(count)
	counts.forEach { if(it == 1){return true}}
	return false
}
fun isIncreasing(password: String) :Boolean {
	for (i in password.dropLast(1).indices) {
		val currentDigit = password[i]
		val nextDigit = password[i + 1]
		if (currentDigit > nextDigit) { return false }
	}
	return true
}

fun main(){
	val password :String
	var numberOfPasswords :Int= 0
	for(number in 264793..803935){
		val password = number.toString()
		if(isIncreasing(password) && isDouble(password)){
			numberOfPasswords++
		}
	}
	println(numberOfPasswords)
}