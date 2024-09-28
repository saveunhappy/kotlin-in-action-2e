package calculator

import com.boycoder.calculator.Operation
import kotlin.system.exitProcess

val help = """
--------------------------------------
使用说明：
1. 输入 1 + 1，按回车，即可使用计算器；
2. 注意：数字与符号之间要有空格；
3. 想要退出程序，请输入：exit
--------------------------------------""".trimIndent()

fun main() {
    while (true) {
        println(help)

        val input = readlnOrNull() ?: continue
        if (input == "exit") exitProcess(0)

        val inputList = input.split(" ")
        val result = calculate(inputList)

        if (result == null) {
            println("输入格式不对")
            continue
        } else {
            println("$input = $result")
        }
    }
}

private fun calculate(inputList: List<String>): Int? {
    if (inputList.size != 3) return null

    val left = inputList[0].toInt()
    val operationSymbol = inputList[1]
    val right = inputList[2].toInt()

    val operation = Operation.entries.find { it.value == operationSymbol } ?: return null

    return when (operation) {
        Operation.ADD -> left + right
        Operation.MINUS -> left - right
        Operation.MULTI -> left * right
        Operation.DIVI -> left / right
    }
}

