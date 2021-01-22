package step6

import kotlin.test.fail

fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done")
}

data class Person2(val name: String,
                  val age: Int? = null) {
    fun isOlderThan(other: Person2): Boolean? {
        if (age == null || other.age == null)
            return null
        return age >= other.age
    }
}

fun main(args: Array<String>) {
    showProgress(146)

    println(Person2("Sam", 35).isOlderThan(Person2("Amy", 42)))
    println(Person2("Sam", 35).isOlderThan(Person2("Amy")))

    val a = 10
    val b = 1_0
    val c = 10L
    println("$a==$b : ${a==b}")
    println("$a==$c : ${a==c.toInt()}")
    println("$a+$c : ${a+c}")

    val person = Person2("Sam")
    val age = person.age ?: fail("empty")
    println(age)
}