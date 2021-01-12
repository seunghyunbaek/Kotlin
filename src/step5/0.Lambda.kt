package scratch.step5

data class Person(val name: String, val age: Int)

fun findTheOldest(people: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null

    for (person in people) {
        if(person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")
    }
}
fun main() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(people)

    println(people.maxBy { it.age })
    println(people.maxBy(Person::age))
    println(people.maxBy() {p:Person -> p.age })
    println(people.maxBy {p: Person -> p.age})

    var names = people.joinToString(separator=" ",
            transform = {p: Person -> p.name})
    println(names)

    names = people.joinToString(" ") {p: Person -> p.name}
    println(names)

    val getAge = {p: Person -> p.age}
    println(people.maxBy(getAge))

    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessagesWithPrefix(errors, "Error:")

    val nextAction = ::printMessagesWithPrefix
    nextAction(errors, "Error:")

    val createPerson = ::Person
    val p = createPerson("Alice", 29)
}