package step6

fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

fun main(args: Array<String>) {
    val email: String? = "solob@email.com"
    // sendEmailTo(email) // Null이 가능한 타입의 변수를 함수의 인자로 넘길 수 없다.
    if (email != null) sendEmailTo(email)

    email?.let { email ->
        sendEmailTo(email)
    }
}

