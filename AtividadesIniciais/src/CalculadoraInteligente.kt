//TODO(Números negativos, números quebrados e abstração)
class CalculadoraInteligente {
    var index: Int = 0
    var numsFinais: ArrayDeque<Double> = ArrayDeque()
    var sinaisFinais: ArrayDeque<Oper> = ArrayDeque()

    constructor()

    fun calcInteligente(sOperacao: String): Double { //5 + 7 * 9 / 2 * (5 - 4) * 2
        inicializarVariaveis()
        val sChar = sOperacao.replace(" ", "").toCharArray() //5+7*9/2*(5-4)*2
        sinaisFinais.add(Oper.VAZIO)
        while (index < sChar.size) {
            var num = 0.0
            val lastSinal = sinaisFinais.last()
            var isNum = isNum(sChar[index])
            if (sChar[index] == '(') {
                index++
                num = parentesesSolver(sChar)
                isNum = true
            } else if (isNum) num = getNum(sChar)
            if (isNum) {
                when (lastSinal) {
                    Oper.MULT -> {
                        sinaisFinais.removeLast()
                        numsFinais.add(numsFinais.removeLast() * num)
                    }

                    Oper.DIV -> {
                        sinaisFinais.removeLast()
                        numsFinais.add(numsFinais.removeLast() / num)
                    }

                    else -> numsFinais.add(num)
                }
            } else {
                sinaisFinais.add(getOperacao(sChar[index]))
                index++
            }
        }
        return solveSumAndSub(numsFinais, sinaisFinais)
    }


    private fun parentesesSolver(sChar: CharArray): Double {
        val numsParenteses: ArrayDeque<Double> = ArrayDeque<Double>()
        val sinaisParenteses: ArrayDeque<Oper> = ArrayDeque<Oper>()
        sinaisParenteses.add(Oper.VAZIO)
        while (sChar[index] != ')') {
            var num = 0.0
            val lastSinal = sinaisParenteses.last()
            var isNum = isNum(sChar[index])
            if (sChar[index] == '(') {
                index++
                num = parentesesSolver(sChar)
                isNum = true
            } else if (isNum) num = getNum(sChar)
            if (isNum) {
                when (lastSinal) {
                    Oper.MULT -> {
                        sinaisParenteses.removeLast()
                        numsParenteses.add(numsParenteses.removeLast() * num)
                    }

                    Oper.DIV -> {
                        sinaisParenteses.removeLast()
                        numsParenteses.add(numsParenteses.removeLast() / num)
                    }

                    else -> numsParenteses.add(num)
                }
            } else {
                sinaisParenteses.add(getOperacao(sChar[index]))
                index++
            }
        }
        index++
        return solveSumAndSub(numsParenteses, sinaisParenteses)
    }

    private fun getNum(sChar: CharArray): Double {
        var actualNum = sChar[index]
        var num = 0.0
        while (index < sChar.size && (actualNum - '0') in 0..9) {
            num = num * 10 + (actualNum - '0')
            index++
            if (index < sChar.size) {
                actualNum = sChar[index]
            }
        }
        return num
    }

    private fun solveSumAndSub(nums: ArrayDeque<Double>, sinais: ArrayDeque<Oper>): Double {
        while (nums.size > 1) {
            when (sinais.removeFirst()) {
                Oper.SOMA -> nums.addFirst(nums.removeFirst() + nums.removeFirst() )
                Oper.SUB -> nums.addFirst(nums.removeFirst() -  nums.removeFirst() )
                else -> 0
            }
        }
        return nums.first()
    }

    private fun getOperacao(char: Char): Oper {
        return when (char) {
            '+' -> Oper.SOMA
            '-' -> Oper.SUB
            '*' -> Oper.MULT
            '/' -> Oper.DIV
            else -> Oper.VAZIO
        }
    }

    private fun isNum(char: Char): Boolean {
        return (char - '0') >= 0 && (char - '0') <= 9
    }

    private fun inicializarVariaveis() {
        index = 0
        numsFinais = ArrayDeque()
        sinaisFinais = ArrayDeque()
    }
}