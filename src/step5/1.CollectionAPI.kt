package scratch.step5

class Book(val title: String, val authors: List<String>)

fun main(args: Array<String>) {
    val list = listOf(1,2,3,4)
    println(list.filter{ it%2 == 0 })
    println(list.map {it * it})

    val people = listOf(Person("Alice", 27), Person("Bob", 31))

    println(people.filter { it.age > 30 })
    println(people.map {it.name})
    println(people.filter {it.age > 30}.map {p:Person -> p.name})
    println(people.filter {it.age > 30}.map(Person::name)) // Person::name ==> {p:Person->p.name} 이다. 코틀린의 문법상 map({lambda}) ==> map {lambda} 로 쓸 수 있음.
    println(people.filter {it.age == people.maxBy { it.age }!!.age})

    val maxAge = people.maxBy { it.age }!!.age
    people.filter {it.age == maxAge}

    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() })

    val canBeInClub27 = { p: Person -> p.age <= 27 }
    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27))
    println(people.count(canBeInClub27))
    println(people.count {it.age <= 27})

    people.count(canBeInClub27) // 조건을 만족하는 원소를 따로 저장하지는 않는다.
    people.filter(canBeInClub27).size // 조건을 만족하는 모든 원소가 들어가는 중간 컬렉션이 생긴다.

    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })

    val books =
            listOf(
                    Book("Thursday Next", listOf("Jasper Fforde")),
                    Book("Mort", listOf("Terry Pratchett")),
                    Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman"))
            )
    println(books.flatMap { it.authors })
    println(books.flatMap { it.authors }.toSet())


}