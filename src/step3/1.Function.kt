package step3

import java.lang.StringBuilder

// 자바 컬렉션에는 디폴트 toString 구현이 들어있다. 하지만 그 디폴트 toString의 출력 형식은 고정돼 있고 우리에게 필요한 형식이 아닐 수도 있다.
fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)
    println(list) // toString 호출

    // 디폴트 구현과 달리 (1; 2; 3)처럼 원소 사이를 세미콜론으로 구분하고 괄호로 리스트를 둘러싸고 싶다면 어떻게 해야할까?
    // 이를 위해서는 자바 프로젝트에 구아바(Guava)나 아파치 커먼즈(Apache Commons)같은 서드파티 프로젝트를 추가하거나 직접 관련 로직을 구현해야 한다.
    // 코틀린에는 이런 요구 사항을 처리할 수 있는 함수가 표준 라이브러리에 이미 들어있다.

    println(joinToString(list, ";", "(", ")"))

    println(joinToString2(list))
    println(joinToString2(list, ";", "(", ")"))
    // 이름 붙인 인자를 사용하는 경우에는 인자 목록의 중간에 있는 인자를 생략하고, 지정하고 싶은 인자를 이름 붙여서 순서와 관계없이 지정할 수 있다.
    println(joinToString2(list, ";", postfix = "(", prefix = ")"))

    println(list.joinToString(separator=";", prefix="[", postfix="]"))
}

// 처음에는 함수 선언을 간단하게 만들 수 있게 코틀린이 지원하는 여러 기능을 사용하지 않고 함수를 직접 구현한다.
// 그 후 좀 더 코틀린답게 같은 함수를 다시 구현한다.

// 리스트의 joinToString 함수는 컬렉션의 원소를 StringBuilder의 뒤에 덧붙인다.
// 이때 원소 사이에 구분자(seperator)를 추가하고, StringBuilder의 맨 앞과 맨 뒤에는 접두사(prefix)와 접미사(postfix)를 추가한다.

// 이 함수는 제네릭(generic)하다. 즉, 이 함수는 어떤 타입의 값을 원소로 하는 컬렉션이든 처리할 수 있다. 제네릭 함수의 문법은 9장에서 자세히 다룬다.
// 이 함수를 그대로 써도 좋을 것이다. 하지만 선언 부분을 좀 더 고민해봐야 한다. 어떻게 하면 이 함수를 호출하는 문장을 덜 번잡하게 만들 수 있을까?
fun <T> joinToString(
        collection: Collection<T>,
        separator: String,
        prefix: String,
        postfix: String
) : String {
    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// 해결하고픈 첫 번째 문제는 함수 호출 부분의 가독성이다.
// joinToString(collection, "", "", ".") 인자로 전달한 각 문자열이 어떤 역할을 하는지 구분할 수 있는가?
// 각 원소는 공백으로 구분될까, 마침표로 구분될까? 함수의 시그니처를 살펴보지 않고는 이런 질문에 답하기 어렵다.
// 이런 문제는 특히 불리언 플래그(flag)값을 전달해야 하는 경우 흔히 발생한다.
// 이런 문제를 해결하기 위해 일부 자바 코딩 스타일에서는 불리언 대신 enum 타입을 사용하라고 권장한다.
// 일부 코딩 스타일에서는 파라미터 이름을 주석에 넣으라고 요구하기도 한다.
// 자바
// joinToString(collection, /*seperator*/"", /*prefix*/"", /*postfix*/".");

// 코틀린에서는 다음과 같이 더 잘 할 수 있다.
// joinToString(collection, seperator="", prefix="", postfix=".")
// 코틀린으로 작성한 함수를 호출할 때는 함수에 전달하는 인자 중 일부(또는 전부)의 이름을 명시할 수 있다.
// 호출 시 인자 중 어느 하나라도 이름을 명시하고 나면 혼동을 막기 위해 그 뒤에 오는 모든 인자는 이름을 꼭 명시해야 한다.

// 자바에서는 일부 클래스에서 오버로딩(overloading)한 메소드가 너무 많아진다는 문제가 있다.
// 오버로딩 메소드들은 하위 호환성을 유지하거나 API 사용자에게 편의를 더하는 등의 여러가지 이유로 만들어지지만 어느 경우든 중복이라는 결과가 같다.
// 파라미터 이름과 타입이 계속 반복되며, 여러분이 친절한 개발자라면 모든 오버로딩 함수에 대해 대부분의 설명을 반복해 다아야 할 것이다.
// 코틀린에서는 함수 선언에서 파라미터의 디폴트 값을 지정할 수 있으므로 이런 오버로드 중 상당수를 피할 수 있다.
// 디폴트 값을 사용해 joinToStrig을 개선해보자

// 이제 함수를 호출할 때 모든 인자를 쓸 수도 있고, 일부를 생략할 수도 있다.
// 함수의 디폴트 파라미터 값은 함수 선언 쪽에서 지정된다. 따라서 어떤 클래스 안에 정의된 함수의 디폴트 값을 바꾸고 그 클래스가 포함된 파일을 재컴파일하면 그 함수를 호출하는 코드 중에 값을 지정하지 않은 모든 인자는 자동으로 바뀐 디폴트 값을 적용받는다.
fun <T> joinToString2(collection: Collection<T>,
        seperator: String = ",",
        prefix: String = "",
        postfix: String =""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(seperator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// 자바에는 디폴트 파라미터 값이라는 개념이 없어서 코틀린 함수를 자바에서 호출하는 경우에는 그 코틀린 함수가 디폴트 파라미터 값을 제공하더라도 모든 인자를 명시해야 한다.
// 자바에서 코틀린 함수를 자주 호출해야 한다면 자바 쪽에서 좀 더 편하게 코틀린 코드를 호출하고 싶을 것이다.
// 그럴 때 @JvmOverloads 애노테이션을 함수에 추가할 수 있다.
// @JvmOverloads를 함수에 추가하면 코틀린 컴파일러가 자동으로 맨 마지막 파라미터로부터 파라미터를 하나씩 생략한 오버로딩한 자바 메소드를 추가해준다.

/*
예를들어 joinToString에 @JvmOverloads를 붙이면 다음과 같은 오버로딩한 함수가 만들어진다.
각가의 오버로딩한 함수들은 시그니처에서 생략된 파라미터에 대해 코틀린 함수의 디폴트 파라미터 값을 사용한다.

String joinToString(Collection<T> collection, String separator, String prefix, String postfix);
String joinToString(Collection<T> collection, String separator, String prefix);
String joinToString(Collection<T> collection, String separator);
String joinToString(Collection<T> collection);
*/