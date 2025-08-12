package ch01.ex1_ATasteOfKotlin

class Person(
    val name: String,
    val age: Int? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (name != other.name) return false
        return age == other.age
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (age ?: 0)
        return result
    }
}

fun main() {
    val persons = listOf(
        Person("Alice", age = 29),
        Person("Bob"),
    )
    val oldest = persons.maxByOrNull {
        it.age ?: 0
    }
    println("The oldest is: $oldest")

    val a = Person("a",1)
    val b = Person("a",1)
    println(a == b)
}

// The oldest is: Person(name=Alice, age=29)
