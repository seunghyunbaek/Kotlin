// 코틀린 최상위 함수가 포함되는 클래스의 이름을 바꾸고 싶다면 파일에 @JvmName 애노테이션을 추가하라.
// @JvmName 애노테이션은 파일의 맨 앞, 패키지 이름 선언 이전에 위치해야한다.
@file:JvmName("StringFunctions") // 클래스이름을 지정하는 어노테이션  // @JvmName을 사용함에 따라 클래스 이름이 JoinKt에서 StringFunctions 클래스로 바뀐다.
package strings
import java.lang.StringBuilder

// 이 함수가 어떻게 실행될 수 있는걸까? JVM이 클래스 안에 들어있는 코드만을 실행할 수 있기 때문에 컴파일러는 이 파일을 컴파일할 때 새로운 클래스를 정의해준다.
// 코틀린만 사용하는 경우에는 그냥 그런 클래스가 생긴다는 사실만 기억하면 된다.
fun <T> joinToString(collection: Collection<T>,
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

// 확장함수를 만들려면 추가하려는 함수 이름 앞에 그 함수가 확장할 클래스의 이름을 덧붙이기만 하면 된다.
// 클래스 이름을 수신 객체 타입(receiver type)이라 부르며, 확장 함수가 호출되는 대상이 되는 값(객체)을 수신 객체(receiver object)라고 부른다.
// 수신 객체 타입은 확장이 정의될 클래스의 타입이며, 수신 객체는 그 클래스에 속한 인스턴스 객체다.
// String은 수신 객체 타입, this는 수신 객체
// 이 함수를 호출하는 구문은 다른 일반 클래스 멤버를 호출하는 구문과 똑같다. (ExtentionFunction.kt 18줄)
//fun String.lastChar(): Char = this.get(this.length - 1)

// 일반 메소드의 본문에서 this를 사용할 때와 마찬가지로 확장 함수 본문에도 this를 쓸 수 있다.
// 그리고 일반 메소드와 마찬가지로 확장 함수 본문에서도 this를 생략할 수 있다.
// 확장 함수 내부에서는 일반적인 인스턴스 메소드의 내부에서와 마찬가지로 수신 객체의 메소드나 프로퍼티를 바로 사용할 수 있다.
// 하지만 확장 함수가 캡슐화를 깨지는 않는다는 사실을 기억하라.
// 클래스 안에서 정의한 메소드와 달리 확장 함수 안에서는 클래스 내부에서만 사용할 수 있는 비공개(private)멤버나 보호된(protected)멤버를 사용할 수 없다.
fun String.lastChar(): Char = get(length -1)

