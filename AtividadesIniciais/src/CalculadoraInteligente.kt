class CalculadoraInteligente {
    //TODO(
    //  - Terminar implementação de parenteses
    // )
    var index: Int = 0
    val numsFinais: ArrayDeque<Double> = ArrayDeque<Double>()
    val sinaisFinais: ArrayDeque<Oper> = ArrayDeque<Oper>()

    constructor()

    fun calcInteligente(sOperacao: String): Double { //5 + 7 * 9 / 2 * (5 - 4) * 2
        val sChar = sOperacao.replace(" ", "").toCharArray() //5+7*9/2*(5-4)*2
        sinaisFinais.add(Oper.VAZIO)
        while (index < sChar.size) {
            if (sChar[index] == '(') {
                index++
                resolveParentese(sChar)
            }
            getValues(sChar)
        }
        return solveSumAndSub(numsFinais, sinaisFinais)
    }

    private fun resolveParentese(sChar: CharArray) {
        val numsParenteses: ArrayDeque<Double> = ArrayDeque<Double>()
        val sinaisFinais: ArrayDeque<Oper> = ArrayDeque<Oper>()
        while (sChar[index] != ')') {
            if (sChar[index] == '(') {
                index++
                //resolveParentese(sChar) // Especificar apenas paraparenteses
            }
            getValues(sChar)
        }
        solveSumAndSub(numsParenteses, sinaisFinais)
    }

    private fun solveSumAndSub(nums: ArrayDeque<Double>, sinais: ArrayDeque<Oper>): Double {
        while (nums.size > 1) {
            when (sinais.removeFirst()) {
                Oper.SOMA -> nums.addFirst(nums.removeFirst() + nums.removeFirst())
                Oper.SUB -> nums.addFirst(nums.removeFirst() - nums.removeFirst())
                else -> 0
            }
        }
        return nums.first()
    }

    private fun getValues(sChar: CharArray) {
        val lastSinal = sinaisFinais.last()
        var num = 0
        var isNum = false
        var actualNum = sChar[index]
        while (index < sChar.size && (actualNum - '0') in 0..9) {
            isNum = true
            num = num * 10 + (actualNum - '0')
            index++
            if (index < sChar.size) {
                actualNum = sChar[index]
            }
        }
        if (lastSinal == Oper.MULT) {
            sinaisFinais.removeLast()
            numsFinais.add(numsFinais.removeLast() * num)
        } else if (lastSinal == Oper.DIV) {
            sinaisFinais.removeLast()
            numsFinais.add(numsFinais.removeLast() / num)
        } else if (isNum) {
            numsFinais.add(num.toDouble())
        } else {
            sinaisFinais.add(
                when (sChar[index].toString()) {
                    "+" -> Oper.SOMA
                    "-" -> Oper.SUB
                    "*" -> Oper.MULT
                    "/" -> Oper.DIV
                    else -> Oper.VAZIO
                }
            )
            index++;
        }
    }
}