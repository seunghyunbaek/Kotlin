package step1

data class Person(val name: String, val age: Int? = null)

fun main(args: Array<String>) {
    val persons = listOf(Person("영희"), Person("철수", age = 29))

    val oldest = persons.maxBy { it.age ?: 0 }

    println("가장 나이가 많은 사람 : $oldest")
}