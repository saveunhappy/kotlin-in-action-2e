package ch09.SplitFilename

data class NameComponents(
    val name: String,
    val extension: String,
)

fun splitFilename(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1])
}

fun main() {
    val (name, ext) = splitFilename("example.kt")
    println(name)
    // example
    println(ext)
    // kt
    val str1 = "example.test.kt"
    val result1 = str1.split('.', limit = 2)
    println(result1)  // 输出: [example, test.kt]

    val str2 = "example.kt"
    val result2 = str2.split('.', limit = 2)
    println(result2)  // 输出: [example, kt]

}
