package com.boycoder.calculator

import java.lang.StringBuilder
import kotlin.system.exitProcess


class CalculatorV3 {

    private val exit = "exit"
    private val help = """
--------------------------------------
使用说明：
1. 输入 1 + 1，按回车，即可使用计算器；
2. 注意：数字与符号之间要有空格；
3. 想要退出程序，请输入：exit
--------------------------------------""".trimIndent()

    fun start() {
        while (true) {
            println(help)

            val input = readlnOrNull() ?: continue
            val result = calculate(input)

            if (result == null) {
                println("输入格式不对")
                continue
            } else {
                println("$input = $result")
            }
        }
    }

    fun calculate(input: String): String? {
        if (shouldExit(input)) exitProcess(0)

        val exp = parseExpression(input) ?: return null

        val left = exp.left
        val operator = exp.operator
        val right = exp.right

        return when (operator) {
            Operation.ADD -> addString(left, right)
            Operation.MINUS -> minusString(left, right)
            Operation.MULTI -> multiString(left, right)
            Operation.DIVI -> diviString(left, right)
        }
    }

    private fun addString(leftNum: String, rightNum: String): String {
        val result = StringBuilder()
        //135 + 99
        //字符串的长度减1正好是数组的下标最大值，所以获得的就是数字是有几位
        //135.length是3，那leftIndex就是2
        var leftIndex = leftNum.length - 1
        //99.length - 1就是1
        var rightIndex = rightNum.length - 1
        //表示进位
        var carry = 0

        while (leftIndex >= 0 || rightIndex >= 0) {
            //这里取的就是字符串的最后一位，那么就是个位数，5
            val leftVal = if (leftIndex >= 0) leftNum[leftIndex].digitToInt() else 0
            //这里就是9，如果到百位，99没有百位，那么就是0
            val rightVal = if (rightIndex >= 0) rightNum[rightIndex].digitToInt() else 0
            //两个数字个位相加，再加上进位
            val sum = leftVal + rightVal + carry
            //除以10只需要知道进位即可
            carry = sum / 10
            //取余数，进位余下的数字
            result.append(sum % 10)
            //取十位
            leftIndex--
            //取十位
            rightIndex--
        }
        if (carry != 0) {
            //如果两个二位数相加超过100，这里就会有用
            result.append(carry)
        }
        //字符串拼接的是从个位到百位，所以需要翻转
        return result.reverse().toString()
    }

    private fun minusString(left: String, right: String): String {
        val result = left.toInt() - right.toInt()
        return result.toString()
    }

    private fun multiString(left: String, right: String): String {
        val result = left.toInt() * right.toInt()
        return result.toString()
    }

    private fun diviString(left: String, right: String): String {
        val result = left.toInt() / right.toInt()
        return result.toString()
    }

    private fun shouldExit(input: String): Boolean {
        return input == exit
    }

    private fun parseExpression(input: String): Expression? {
        val operation = parseOperator(input) ?: return null
        val list = input.split(operation.value)
        if (list.size != 2) return null
        return Expression(
            left = list[0].trim(),
            operator = operation,
            right = list[1].trim()
        )
    }

    private fun parseOperator(input: String): Operation? {
        Operation.entries.forEach {
            if (input.contains(it.value)) {
                return it
            }
        }
        return null
    }
}

fun main() {
    val calculator = CalculatorV3()
    calculator.start()
}

