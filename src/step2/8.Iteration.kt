package step2

import java.util.*

// 코틀린 특성중 자바와 가장 비슷한 것이 이터레이션이다.
// 코틀린 while 루프는 자바와 동일하다.

// 코틀린에는 자바의 for 루프(어떤 변수를 초기화하고 그 변수를 루프를 한 번 실행할 때마다 갱신하고 루프 조건이 거짓이 될 때 반복을 마치는 형태의 루프)에 해당하는 요소가 없다.
// 이런 루프의 초깃값, 증가 값, 최종 값을 사용한 루프를 대신하기 위해 코틀린에서는 범위(range)를 사용한다.

// 범위는 기본적으로 정수 등의 숫자 타입의 두 값으로 이뤄졌으며, .. 연산자로 시작 값과 끝 값을 연결해서 범위를 만든다.
// 코틀린의 범위는 폐구간(닫힌 구간) 또는 양끝을 포함하는 구간이다.
// 정수 범위로 수행할 수 있는 가장 단순한 작업은 범위에 속한 모든 값에 대한 이터레이션이다. 이런 식으로 어떤 범위에 속한 값을 일정한 순서로 이터레이션하는 경우를 수열(progression)이라고 부른다.

// 간단한 피즈버즈(Fizz-Buzz) 게임을 위해 정수 범위를 사용해보자.
// 순차적으로 수를 세면서 3으로 나눠떨어지면 피즈, 5로 나눠떨어지면 버즈, 3과 5로 나눠떨어지면 피즈버즈라고 말하는 게임이다.
// 인자가 없는 when을 사용해서 1 부터 100까지의 피즈버즈 결과가 보이도록 해보자.

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz\n"
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

fun main(args: Array<String>) {
    for(i in 1..100) { // 1..100 범위의정수에 대해 이터레이션한다.
        print(fizzBuzz(i))
    }
    println("\n")

    // 이번에는 100부터 거꾸로 세되, 짝수만 가지고 해보자.
    for(i in 100 downTo 1 step 2) { // 증가 값 step을 갖는 수열에 대해 이터레이션 한다. 증가 값을 사용하면 수를 건너 뛸 수 있다. 증가 값을 음수로 만들면 정방향 수열이 아닌 역방향 수열을 만들 수 있다.
        // 여기서 100 downTo 1은 역방향 수열을 만든다(역방향 수열의 기본 증가 값은 -1이다). 그 뒤에 step2를 붙이면 증가 값의 절댓값이 2로 바뀐다.
        print(fizzBuzz(i))
    }
    println("\n")

    // 끝 값을 포함하지 않는 반만 닫힌 범위에 대해 이터레이션 할 수도 있다.
    // 이때는 until 함수를 사용한다. for(x in 0 until size)라는 루프는 for(x in 0..size-1)과 같지만 좀 더 명확하게 개념을 표현한다. 이는 나중에 더 자세히 다루도록 하자.

    // 맵에 대한 이터레이션
    // 문자에 대한 2진 표현을 출력하는 프로그램을 살펴보자. 이때 2진 표현을 맵에 저장하자(단지 예제로 쓰기 위한 목적이다).
    val binaryReps = TreeMap<Char, String>() // 키에 대해 정렬하기 위해 TreeMap을 사용한다.

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt()) // 아스키(ASCⅡ) 코드를 2진 표현으로 바꾼다.
        binaryReps[c] = binary // c를 키로 c의 2진 표현을 맵에 넣는다.
    }

    // 맵에 대해 이터레이션한다. 맵의 키와 값을 두 변수에 각각 대입한다.
    for ((letter, binary) in binaryReps) { // 이터레이션하려는 컬렉션의 원소를 푸는 방법을 보여준다(맵은 키/값 쌍을 원소로하는 컬렉션이다). 나중에 객체를 풀어서각 부분을 분리하는 구조에 대해 자세히 다룬다.
        println("$letter = $binary")
    }
    println("\n")

    // 맵에 사용했던 구조 분해 구문을 맵이 아닌 컬렉션에도 활용할 수 있다.
    // 그런 구조 분해 구문을 사용하면 원소의 현재 인덱스를 유지하면서 컬렉션을 이터레이션할 수 있다.
    // 인덱스를 저장하기 위한 변수를 별도로 선언하고 루프에서 매번 그 변수를 증가시킬 필요가 없다.
    val list = arrayListOf("10", "11", "1001")
    for((index, element) in list.withIndex()) { // 인덱스와 함께 컬렉션을 이터레이션한다. withIndex의 정체는 step3에서 살펴보자.
        println("$index: $element")
    }
    println("\n")

    // 한편 어떤 값이 범위나 컬렉션에 들어있는지 알고 싶을 때도 in을 사용한다.
    // in 연산자를 사용해 어떤 값이 범위에 속하는지 검사할 수 있다.
    // !in 연산자를 사용하면 어떤 값이 범위에 속하지 않는지 검사할 수 있다.
    fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z' // 이런 비교 로직은 표준 라이브러리의 범위 클래스 구현안에 깔끔하게 감춰져 있다. 'a' <= c && c <= 'z' 식으로 변환된다.
    fun isNotDigit(c: Char) = c !in '0'..'9'
    println(isLetter('q'))
    println(isNotDigit('x'))

    // when에서 in 사용하기
    fun recognize(c: Char) = when(c) {
        in '0'..'9' -> "It is digit"
        in 'a'..'z', in 'A'..'Z' -> "It is letter"
        else -> "I don't know..."
    }
    println(recognize('8'))

    // 범위는 문자에만 국한되지 않는다. 비교 가능한 클래스라면(java.lang.Comparable 인터페이스를 구현한 클래스라면) 그 클래스의 인스턴스 객체를 사용해 범위를 만들 수 있다.
    // Compareable을 사용하는 범위의 경우 그 범위 내의 모든 객체를 항상 이터레이션할 수 있을까? 그럴 수 없다.
    // 하지만 in 연산자를 사용하면 값이 범위 안에 속하는지 항상 결정할 수 있다.
    // String에 있는 Comparable 구현이 두 문자열을 알파벳 순서로 비교하기 때문에 여기있는 in 검사에서도 문자열을 알파벳 순서로 비교한다.
    println("Kotlin" in "Java".."Scala")
    // 컬렉션에도 마찬가지로 in 연산을 사용할 수 있다.
    println("Kotlin" in setOf("Java", "Scala")) // 이 집합에는 Kotlin이 들어있지 않다.
}