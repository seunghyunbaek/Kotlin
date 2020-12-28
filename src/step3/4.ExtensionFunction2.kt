package step3

// 임포트와 확장함수
// 확장 함수를 정의했다고 해도 자동으로 프로젝트 안의 모든 소스코드에서 그 함수를 사용할 수 있지는 않다.
// 확장 함수를 사용하기 위해서는 그 함수를 다른 클래스나 함수와 마찬가지로 임포트해야만 한다.
//import strings.lastChar
//val c = "Kotlin".lastChar()

import strings.lastChar as last // as 키워드를 사용하면 임포트한 클래스나 함수를 다른 이름으로 부를 수 있다.
val c = "Kotlin".last()
// 한 파일 안에서 다른 여러 패키지에 속해있는 이름이 같은 함수를 가져와 사용해야 하는 경우 이름을 바꿔서 임포트하면 이름 충돌을 막을 수 있다.
// 물론 일반적인 클래스나 함수라면 그 전체 이름(FQN, Fully Qualified Name)을 써도 된다.
// 그러나 코틀린 문법상 확장 함수는 반드시 짧은 이름을 써야 한다.
// 따라서 임포트할 때 이름을 바꾸는 것이 확장 함수 이름 충돌을 해결할 수 있는 유일한 방법이다.

// 내부적으로 확장 함수는 수신 객체를 첫 번째 인자로 받는 정적 메소드다.
// 그래서 확장 함수를 호출해도 다른 어댑터(adapter)객체나 실행 시점 부가 비용이 들지 않는다.
// 이런 설계로 인해 자바에서 확장 함수를 사용하기도 한다. 단지 정적 메소드를 호출하면서 첫 번째 인자로 수신 객체를 넘기기만 하면 된다.
/* 확장함수를 StringUtil.kt 파일에 정의했다면 다음과 같이 호출할 수 있다.
자바
char c = StringUtilKt.lastChar("Java");
*/

// 이제 joinToString 함수의 최종 버전을 만들자. 이제 이 함수는 코틀린 라이브러리가 제공하는 함수와 거의 같아졌다.
fun <T> Collection<T>.joinToString(
        separator: String=",",
        prefix: String="",
        postfix: String=""
): String {
    val result = StringBuilder(prefix)

    for((index, element) in this.withIndex()) { // "this"는 수신 객체를 가리킨다. 여기서는 T 타입의 원소로 이뤄진 컬렉션이다.
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)

    return result.toString()
}

// 확장 함수는 단지 정적 메소드 호출에 대한 문법적인 편의(syntatic sugar)일 뿐이다.
// 그래서 클래스가 아닌 더 구체적인 타입을 수신 객체 타입으로 지정할 수도 있다.
// 그래서 문자열의 컬렉션에 대해서만 호출할 수 있는 join 함수를 정의하고 싶다면 다음과 같이 하면 된다.
fun Collection<String>.join(
        separator:String = ",",
        prefix: String = "",
        postfix: String = ""
) = joinToString(separator, prefix, postfix)

// 확장 함수는 오버라이드할 수 없다
// 코틀린의 메소드 오버라이드도 일반적인 객체지향의 메소드 오버라이드와 마찬가지다.
// 하지만 확장 함수는 오버라이드할 수 없다.
// View와 그 하위 클래스인 Button이 있는데, Button이 상위 클래스의 click 함수를 오버라이드하는 경우를 생각해보자.

open class View {
    open fun click() = println("View clicked")
}

class Button: View() {
    override fun click() = println("Button clicked")
}

// Button이 View의 하위 타입이기 때문에 View 타입 변수를 선언해도 Button 타입 변수를 그 변수에 대입할 수 있다.
// View 타입 변수에 대해 click과 같은 일반 메소드를 호출했는데, click을 Button 클래스가 오버라이드 했다면 실제로는 Button이 오버라이드한 click이 호출된다.

// 하지만 확장은 이런 상속의 관계처럼 작동하지 않는다.
// 확장 함수는 클래스의 일부가 아니다. 확장 함수는 클래스 밖에 선언된다.
// 이름과 파라미터가 완전히 같은 확장 함수를 기반 클래스와 하위 클래스에 대해 정의해도 실제로는 확장 함수를 호출할 때 수신 객체로 지정한 변수의 정적 타입에 의해 어떤 확장 함수가 호출될지 결정되지, 그 변수에 저장된 객체의 동적인 타입에 의해 확장 함수가 결정되지 않는다.
// 확장 함수를 첫 번째 인자가 수신 객체인 정적 자바 메소드로 컴파일한다는 사실을 기억한다면 이런 동작을 쉽게 이해할 수 있다.
/*
자바
View view = new Button();
ExtensionsKt.showOff(view);
 */
fun View.showOff() = println("I'm a view!")
fun Button.showOff() = println("I'm a button!")

// 노트
// 어떤 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같다면 확장 함수가 아니라 멤버 함수가 호출된다(멤버 함수의 우선순위가 더 높다).

// 확장 프로퍼티
// 확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의 구문으로 사용할 수 있는 API를 추가할 수 있다.
// 프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할 적절한 방법이 없기 때문에(기존 클래스의 인스턴스 객체에 필드를 추가할 방법은 없다) 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다.
// 하지만 프로퍼티 문법으로 더 짧게 코드를 작성할 수 있어서 편한 경우가 있다.
val String.lastChar: Char
    get() = get(length-1)

// 확장 함수의 경우와 마찬가지로 확장 프로퍼티도 일반적인 프로퍼티와 같은데, 단지 수신 객체 클래스가 추가 됐을 뿐이다.
// 뒷받침하는 필드가 없어서 기본 게터 구현을 제공할 수 없으므로 최소한 게터는 꼭 정의해야 한다.
// 마찬가지로 초기화 코드에서 값을 담을 장소가 전혀 없으므로 초기화 코드도 쓸 수 없다.
// StringBuilder에 같은 프로퍼티를 정의한다면 StringBuilder의 마지막 문자는 변경 가능하므로 프로퍼티를 var로 만들 수 있다.
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

// 확장 프로퍼티를 사용하는 방법은 멤버 프로퍼티를 사용하는 것과 같다.

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)
    println(list.joinToString(separator = ";", prefix = "(", postfix = ")"))
    println(list.joinToString(""))
    println(list.joinToString())

    println(listOf("one", "two", "eight").join(" "))
    // join 함수를 객체의 리스트에 대해 호출할 수는 없다.
    // 확장 함수가 정적 메소드와 같은 특징을 가지므로, 확장 함수를 하위 클래스에서 오버라이드할 수는 없다.
    // println(listOf(1, 2, 3).join())

    val view:View = Button()
    view.click()
    view.showOff() // View 타입이기에 View의 확장함수가 호출되었다. 확장함수는 변수의 정적 타입이 어떤 확장 함수를 호출할지 결정한다. 변수에 저장된 객체의 동적 타입에 의해 확장 함수가 결정되지 않는다.

    println("Kotlin".lastChar)
    val sb = StringBuilder("Kotlin?")
    sb.lastChar = '!'
    println(sb)
}