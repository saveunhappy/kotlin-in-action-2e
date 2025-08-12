package ch10.ReturnWithLabel

data class Person(val name: String, val age: Int)

val people = listOf(Person("Alice", 29), Person("Bob", 31))

fun lookForAlice(people: List<Person>) {
    //这个return@label就是返回到刚开始执行的地方，重新循环
    //如果不使用这种方式，那第一个碰到不是的，直接就结束循环了
    //就不会接着遍历，所以要使用标签
    people.forEach label@{
        if (it.name != "Alice") return@label
        print("Found Alice!")
    }
}

fun main() {
    lookForAlice(people)
    // Found Alice!
}
