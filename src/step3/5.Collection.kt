package step3

// 컬렉션 처리 : 가변 길이 인자, 중위 함수 호출, 라이브러리 지원
// 컬렉션을 처리할 때 쓸 수 있는 코틀린 표준 라이브러리 함수 몇 가지를 보자.
// 그 과정에서 다음과 같은 코틀린 언어 특성을 설명한다.

/*
* vararg 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 함수를 정의할 수 있다.
* 중위(infix) 함수 호출 구문을 사용하면 인자가 하나뿐인 메소드를 간편하게 호출할 수 있다.
* 구조 분해 선언(destructuring declaration)을 사용하면 복합적인 값을 분해해서 여러 변수에 나눠 담을 수 있다.
*/

// 0.Collection.kt 에서 리스트의 마지막 원소를 가져오는 예제와 숫자로 이뤄진 컬렉션의 최댓값을 찾는 예제를 봤다.
// 그런 코드가 어떻게 작동할 수 있는지 궁금했을 것이다. 어떻게 자바 라이브러리 클래스의 인스턴스인 컬렉션에 대해 코틀린이 새로운 기능을 추가할 수 있었을까?
// 이제는 그 답을 안다. 바로 확장 함수였던 것이다. 코틀린 표준 라이브러리는 수많은 확장 함수를 포함한다.

// 3장을 시작하면서 컬렉션을 만들어내는 함수를 몇 가지 살펴봤다. 그런 함수가 모두 가진 특징은 바로 인자의 개수가 그때그때 달라질 수 있다는 점이다.
// 그럼 파라미터 개수가 달라질 수 있는 함수를 정의하는 방법을 살펴보자
// listOf 함수의 정의를 보면 다음과 같다.
//val list = listOf(2, 3, 5, 7, 11)
/*
public fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()
자바의 가변 길이 인자 varargs에 익숙한 독자가 많을 것이다. 가변 길이 인자는 메소드를 호출할 때 원하는 개수만큼 값을 인자로 넘기면 자바 컴파일러가 배열에 그 값들을 넣어주는 기능이다.
코틀린의 가변 길이 인자도 자바와 비슷하다. 다만 문법이 조금 다르다. 타입 뒤에 ...를 붙이는 대신 코틀린에서는 파라미터 앞에 vararg 변경자를 붙인다.

이미 배열에 들어있는 원소를 가변 길이 인자로 넘길 때도 코틀린과 자바 구문이 다르다.
자바에서는 배열을 그냥 넘기면 되지만 코틀린에서는 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되게 해야 한다.
기술적으로는 스프레드(spread) 연산자가 그런 작업을 해준다.
하지만 실제로는 전달하려는 배열 앞에 *를 붙이기만 하면 된다.
 */

// 이제 맵으로 대상을 옮겨서 코틀린 함수 호출의 가독성을 향상시킬 수 있는 다른 방법인 중위 호출에 대해 살펴보자.
// 맵을 만들려면 mpaOf 함수를 사용한다.

// 인자가 하나뿐인 일반 메소드나 인자가 하나뿐인 확장 함수에 중위 호출을 사용할 수 있다.
// 함수(메소드)를 중위 호출에 사용하게 허용하고 싶으면 infix 변경자를 함수(메소드) 선언 앞에 추가해야 한다.

// to 함수의 정의를 간략하게 줄인 코드다.
// 이 to 함수는 Pair의 인스턴스를 반환한다. Pair는 코틀린 표준 라이브러리 클래스로, 그 이름대로 두 원소로 이뤄진 순서쌍을 표현한다.
// 실제로 to는 제네릭 함수지만 여기서는 설명을 위해 그런 세부 사항을 생략했다.
// Pair의 내용으로 두 변수를 즉시 초기화할 수 있다.

infix fun Any.to(other: Any) = Pair(this, other)

fun main(args: Array<String>) {
    // 이 예제는 스프레드 연산자를 통하면 배열에 들어있는 값과 다른 여러 값을 함께 써서 함수를 호출할 수 있음을 보여준다.
    // 이런 기능은 자바에서는 사용할 수 없다.
    val arg = arrayOf("a", "b", "d", 1)
    val list = listOf("args: ", *arg) // 스프레드 연산자가 배열의 내용을 펼쳐준다.
    println(list)

    // 여기서 to라는 단어는 코틀린 키워드가 아니다. 이 코드는 중위 호출(infix call)이라는 특별한 방식으로 to라는 일반 메소드를 호출한 것이다.
    // 중위 호출 시에는 수신 객체와 유일한 메소드 인자 사이에 메소드 이름을 넣는다(이때 객체, 메소드 이름, 유일한 인자 사이에는 공백이 들어가야 한다).
    val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
    // 다음 두 호출은 동일하다.
    1.to("one") // "to" 메소드를 일반적인 방식으로 호출함
    1 to "one" // "to" 메소드를 중위 호출 방식으로 호출함

    // 이 to 함수는 Pair의 인스턴스를 반환한다. Pair는 코틀린 표준 라이브러리 클래스로, 그 이름대로 두 원소로 이뤄진 순서쌍을 표현한다.
    // 실제로 to는 제네릭 함수지만 여기서는 설명을 위해 그런 세부 사항을 생략했다.

    // Pair의 내용으로 두 변수를 즉시 초기화
    // 이런 기능을 구조 분해 선언(destructuring declaration)이라고 부른다.
    val (number, name) = 1 to "one"

    // Pair 인스턴스 외 다른 객체에도 구조분해를 적용할 수 있다.
    // 7.4절에서는 식의 구조 분해와 구조 분해를 사용해 여러 변수를 초기화하는 방법에 대한 일반 규칙을 다른다.
    val collection = listOf(1, 2,3, 4)
    for((index, element) in collection.withIndex()) {
        println("$index: $element")
    }

    // to는 확장 함수다. to를 사용하면 타입과 관계없이 임의의 순서쌍을 만들 수 있다.
    // 이는 to의 수신 객체가 제네릭하다는 뜻이다.

    // mapOf 함수의 선언을 살펴보자.
    // listOf와 마찬가지로 mapOf에도 원하는 개수만큼 인자를 전달할 수 있다. 하지만 mapOf의 경우에는 각 인자가 키와 값으로 이뤄진 순서쌍이어야 한다.
    mapOf(1 to "one", 2 to "two")

    // 코틀린을 잘 모르는 사라밍 보면 새로운 맵을 만드는 구문은 코틀린 맵에 대해 제공하는 특별한 문법인 것처럼 느껴진다.
    // 하지만 실제로는 일반적인 함수를 더 간결한 구문으로 호출하는 것일 뿐이다.
}