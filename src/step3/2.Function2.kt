package step3
import strings.joinToString

// 지금까지는 함수를 사용할 때 그 함수를 선언하는 시점의 주위 환경을 신경 쓰지 않았다.
// 지금까지 설명한 함수들을 어떤 클래스 안에 선언해야만 할 것이다.
// 그러나 실제로 코틀린에서는 함수를 클래스 안에 선언할 필요가 없다.

// 자바를 아는 사람은 객체지향 언어인 자바에서는 모든 코드를 클래스의 메소드로 작성해야 한다는 사실을 알고 있다. 보통 그런 구조는 잘 작동한다.
// 하지만 실전에서는 어느 한 클래스에 포함시키기 어려운 코드가 많이 생긴다. 일부 연산에는 비슷하게 중요한 역할을 하는 클래스가 둘 이상 있을 수도 있다.
// 중요한 객체는 하나뿐이지만 그 연산을 객체의 인스턴스 API에 추가해서 API를 너무 크게 만들고 싶지는 않은 경우도 있다.
// 그 결과 다양한 정적 메소드를 모아두는 역할만 담당하며, 특별한 상태나 인스턴스 메소드는 없는 클래스가 생겨난다.
// JDK의 Collections 클래스가 전형적인 예다.

// 코틀린에서는 이런 무의미한 클래스가 필요 없다. 대신 함수를 직접 소스 파일의 최상위 수준, 모든 다른 클래스의 밖에 위치시키면 된다.
// 그런 함수들은 여전히 그 파일의 맨 앞에 정의된 패키지의 멤버 함수이므로 다른 패키지에서 그 함수를 사용하고 싶을 때는 그 함수가 정의된 패키지를 임포트해야만 한다.
// 하지만 임포트 시 유틸리티 클래스 이름이 추가로 들어갈 필요는 없다.
// joinToString 함수를 strings 패키지에 넣어보자.

// 최상위 프로퍼티
// 함수와 마찬가지로 프로퍼티도 파일의 최상위 수준에 놓을 수 있다.
// 어떤 데이터를 클래스 밖에 위치시켜야 하는 경우는 흔하지는 않지만, 그래도 가끔 유용할 때가 있다.
// 예를 들어 어떤 연산을 수행한 횟수를 저장하는 var 프로퍼티를 만들 수 있다.

var opCount = 0 // 이런 프로퍼티의 값은 정적 필드에 저장된다.

fun performOperation() {
    opCount++
}

fun reportOperationCount() {
    println("Operation performed $opCount times")
}

// 최상위 프로퍼티를 활용해 코드에 상수를 추가할 수 있다.
//val UNIX_LINE_SEPARATOR = "\n"

// 기본적으로 최상위 프로퍼티도 다른 모든 프로퍼티처럼 접근자 메소드를 통해 자바 코드에 노출된다(val의 경우 게터, var의 경우 게터와 세터가 생긴다).
// 겉으론 상수처럼 보이는데, 실제로는 게터를 사용해야 한다면 자연스럽지 못하다.
// 더 자연스럽게 사용하려면 이 상수를 public static final 필드로 컴파일해야 한다.
// const 변경자를 추가하면 프로퍼티를 public static final 필드로 컴파일하게 만들 수 있다(단, 원시 타입과 String타입의 프로퍼티만 const로 지정할 수 있다).
const val UNIX_LINE_SEPARATOR = "\n"

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)
    println(joinToString(list))

    performOperation()
    reportOperationCount()
    println(opCount)
}