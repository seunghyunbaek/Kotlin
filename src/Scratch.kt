package scratch

import step4.Client
import java.lang.StringBuilder

class User(val name: String, val address: String) {
    infix fun handshake(person: User) = println("handshake $name and ${person.name}") // 인자가 하나라면 infix도 고려해볼만 함
    infix fun isOlder(person: User) = name > person.name // 장점 : 점(.), 괄호(()) 같은 것을 안쓰고 작성하면 되는 편함이 있다.
}

data class User2(val name: String, val age: Int)


fun saveUser(user: User) {
    user.validateBefore()
}

fun User.validateBefore() {
    fun validate(field: String, fieldName: String) {
        if (field.isEmpty())
            throw IllegalArgumentException("$fieldName is Empty")
    }
    validate(name, "Name")
    validate(address, "Address")
}

interface C { fun showOff() = println("C.showOff()")}
interface D { fun showOff() = println("D.showOff()")}
open class E: C, D {
    init { println("E instance created") }

    final override fun showOff() {
        println("E.showOff()")
    }

    open fun scatch() = println("E.scatch()")
}

class F: E {
    constructor() {
        println("F instance created")
    }
    override fun scatch() = println("F.scatch()")
}

object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) {
            println(person)
        }
    }
}

data class Person(val name: String) {
    object NameComparator: Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int = p1.name.compareTo(p2.name)
    }
}


interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person2(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person {
            return Person("dk")
        }
    }
}

fun main() {
    val user = User("A", "65")
    val user2 = User("B", "66")
    user handshake user2 // user.handshake(user2) // infix 함수이므로 "객체 메소드 인자" 의 형태로 사용이 가능함
    if (user isOlder user2) println("A is Older") else println("B is Older")

    val e:C = E()
    e.showOff()

    val f = F()
    f.scatch()
    f.showOff()

    val list = arrayOf("a", "b", "c")

    val args = listOf("args: ", *list)
    println(args)

    val lee = Client("이계영", 4122)
    println(lee.copy(postalCode = 4000))
    println(lee)

    Payroll.calculateSalary()

    val p1 = Person("solob")
    val p2 = Person("soloe")
    println(Person.NameComparator.compare(p1, p2))
}