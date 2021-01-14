package scratch.step5

import java.io.File

fun main(args: Array<String>) {

    val people = listOf(Person("Alice", 27), Person("Bob", 31))

    println(people.map(Person::name).filter { it.startsWith("A") })

    people.asSequence().map(Person::name).filter { it.startsWith("A")}
    people.asSequence().map(Person::name).filter { it.startsWith("A")}.toList()

    listOf(1, 2, 3, 4).asSequence()
            .map { print("map($it) "); it * it}
            .filter { print("filter($it)"); it %2 == 0}

    listOf(1, 2, 3, 4).asSequence()
            .map { print("map($it) "); it * it}
            .filter { print("filter($it)"); it %2 == 0}
            .toList()
    println("")

    var cnt = 0
    listOf(1, 2, 3, 4).asSequence()
            .map { cnt++; it * it }
            .find { it > 3 }
    println("Sequence: $cnt")

    cnt = 0
    listOf(1, 2, 3, 4)
            .map { cnt++; it * it }
            .find { it > 3}
    println("Collection: $cnt")

    val naturalNumbers = generateSequence(0, {it + 1})
    val numbresTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbresTo100.sum())

    val file = File("/Users/svtk/.HiddenDir/a.txt")
    println(file.isInsideHiddenDirectory())

    createAllDoneRunnable().run()
}

fun File.isInsideHiddenDirectory() = generateSequence(this) { it.parentFile }.any { it.isHidden }

fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}
