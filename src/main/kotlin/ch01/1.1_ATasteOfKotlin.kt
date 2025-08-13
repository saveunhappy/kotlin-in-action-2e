package ch01.ex1_ATasteOfKotlin

data class Person(
    val name: String,
    val age: Int? = null
)


fun main() {
    val persons = listOf(
        Person("Alice", age = 29),
        Person("Bob"),
    )
    val oldest = persons.maxByOrNull {
        //源代码中会取出每一个person,然后调用类似于Java中的Function函数
        //传过去一个person,返回一个person.age，那也就是map,然后二分法比较
        //进行筛选，如果是null，那么就用猫王运算符，赋值为0.
        it.age ?: 0
    }
    println("The oldest is: $oldest")

    val a = Person("a",1)
    val b = Person("a",1)
    println(a == b)
}

// The oldest is: Person(name=Alice, age=29)
