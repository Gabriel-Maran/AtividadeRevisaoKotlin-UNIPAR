//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println(calcInteligenteTwoNums("5 * 7"))
    println(calcInteligente("5 + 7 * 9 / 2 (5 - 4) * 2"))
}

fun calcInteligenteTwoNums(sOperacao: String): Int {
    //Se: 5 + 7
    val charArray = sOperacao.replace(" ", "").toCharArray() //5 + 7 ->['5', '+', '7']
    val num1 = charArray[0]//5
    val num2 = charArray[2]//7
    return when (charArray[1]) {
        '+' -> (num1 - '0') + (num2 - '0')
        '-' -> (num1 - '0') - (num2 - '0')
        '*' -> (num1 - '0') * (num2 - '0')
        '/' -> (num1 - '0') / (num2 - '0')
        else -> 0
    }
}

fun calcInteligente(sOperacao: String): Double {
    val sChar = sOperacao.replace(" ", "").toCharArray()
    val nums = ArrayDeque<Double>()
    val sinais = ArrayDeque<String>()
    var index = 0
    sinais.add("+")
    while (index < sChar.size) {
        if (sOperacao[index] == '(') {
            val resolveParentese = resolveParentese(index, sChar)
            index = resolveParentese[1].toInt()
            nums.add(resolveParentese[0])
        }
        val lastSinal = sinais[sinais.size - 1]
        var num = 0
        var isNum = false
        var actualNum = sChar[index]
        while ((actualNum - '0') in 0..9) {
            isNum = true
            num = num * 10 + (actualNum - '0')
            index++
            actualNum = sChar[index]
        }
        if (lastSinal == "*") {
            sinais.removeLast()
            nums.add(nums.removeLast() * num)
            index--
        } else if (lastSinal == "/") {
            sinais.removeLast()
            nums.add(nums.removeLast() / num)
            index--
        }
        if (isNum) nums.add(num.toDouble())
        else {
            sinais.add(sChar[index].toString())
            index++;
        }
    }
    sinais.removeFirst()
    while(nums.size > 1){
        when(sinais.removeFirst()){
            "+" -> nums.add(nums.removeFirst() + nums.removeFirst())
            "-" -> nums.add(nums.removeFirst() - nums.removeFirst())
        }
    }
    return nums[0]
}

fun resolveParentese(index: Int, sChar: CharArray) : MutableList<Double> {
    var i = index
    val nums = ArrayDeque<Int>()
    val sinais = ArrayDeque<String>()
    sinais.add("+")
    while (sChar[i] != ')') {
        val lastSinal = sinais[sinais.size - 1].toString()
        var num = 0
        var isNum = false
        var actualNum = sChar[i]
        while ((actualNum - '0') in 0..9) {
            isNum = true
            num = num * 10 + (actualNum - '0')
            i++
            actualNum = sChar[i]
        }
        if (lastSinal == "*") {
            sinais.removeLast()
            nums.add(nums.removeLast() * num)
        } else if (lastSinal == "/") {
            sinais.removeLast()
            nums.add(nums.removeLast() / num)
        }
        if (isNum) nums.add(num)
        else {
            sinais.add(sChar[i].toString())
            i++;
        }
    }
    sinais.removeFirst()
    while(nums.size > 1){
        when(sinais.removeFirst()){
            "+" -> nums.add(nums.removeFirst() + nums.removeFirst())
            "-" -> nums.add(nums.removeFirst() - nums.removeFirst())
        }
    }
    return mutableListOf(nums[0].toDouble(), index.toDouble())
}
