package calculator

import strings.lastChar

// 不为空的接收者类型
//     ↓
fun String.lastElement(): Char = this[this.length - 1]

// 可为空的接收者类型
//     ↓
fun String?.lastElement(): Char?  = this?.get(this.length - 1)

fun main() {
    println(null.lastElement())
}
