package step6

import java.lang.Exception
import java.lang.NullPointerException

fun printAllCaps(s: String?) {
    val allCaps: String? = s?.toUpperCase()
    println("$allCaps : ${allCaps?.javaClass?.name}")
}

class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee) : String? = employee.manager?.name

fun foo(s: String?) {
    val t: String = s ?: ""
}

fun strLenSafe(s: String?): Int = s?.length ?: 0

class Person(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person ?: return false
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int = firstName.hashCode() * 37 + lastName.hashCode()
}

fun ignoreNulls(s: String?) {
    try {
        val sNotNull: String = s!!
        println(sNotNull.length)
    } catch (e: Exception) {
        println(e)
    }
}
fun main(args: Array<String>) {
    printAllCaps(null)
    printAllCaps("abc")

    val ceo = Employee("Da Boss", null)
    val developer = Employee("Bob Smith", ceo)
    println(managerName(developer))
    println(managerName(ceo))

    println(strLenSafe(null))

    val p1 = Person("Dimitry", "Jemerov")
    val p2 = Person("Dimitry", "Jemerov")
    println(p1==p2)

    ignoreNulls(null)
}