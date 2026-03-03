//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println(calcInteligenteTwoNums("5 * 7"))
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