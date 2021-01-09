package step3

fun main(args: Array<String>) {
    val set = hashSetOf(1, 7, 53)

    val list = arrayListOf(1, 7, 53)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three") // to는 특별한 키워드가 아닌 일반 함수라는 점에 유의하자.

    // javaClass는 자바 getClass()에 해당하는 코틀린 코드다.
    println(set.javaClass) // class java.util.HashSet
    println(list.javaClass)  // class java.util.ArrayLsit
    println(map.javaClass) // class java.util.HashMap
    // 이는 코틀린이 자신만의 컬렉션 기능을 제공하지 않는다는 뜻이다.

    // 코틀린이 자체 컬렉션을 제공하지 않는 이유는 뭘까?
    // 표준 자바 컬렉션을 활용하면 자바 코드와 상호작용하기가 훨씬 더 쉽다. 자바에서 코틀린 함수를 호출하거나 코틀린에서 자바 함수를 호출할 때 자바와 코틀린 컬렉션을 서로 변환할 필요가 없다.
    // 코틀린 컬렉션은 자바 컬렉션과 똑같은 클래스다. 하지만 코틀린에서는 자바보다 더 많은 기능을 쓸 수 있다.
    // 예를 들어 리스트의 마지막 원소를 가져오거나 수로 이뤄진 컬렉션에서 최대값을 찾을 수 있다.

    val strings = listOf("first", "second", "fourteenth")
    println(strings.last())

    val numbers = setOf(1, 14, 2)
    println(numbers.max())
}