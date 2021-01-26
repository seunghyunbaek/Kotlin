package scratch

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
}