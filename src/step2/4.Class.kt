package step2

// 클래스라는 개념의 목적은 데이터를 캡슐화(encapsulate)하고 캡슐화한 데이터를 다루는 코드를 한 주체 아래 가두는 것이다.
// 자바에서는 데이터를 필드(field)에 저장하며, 멤버 필드의 가시성은 보통 비공개(private)다.
// 클래스는 자신을 사용하는 클라이언트가 그 데이터에 접근하는 통로로 쓸 수 있는 접근자 메소드(accessor method)를 제공한다.
// 보통은 필드를 읽기 위한 게터(getter)를 제공하고 필드를 변경하게 허용해야 할 경우 세터(setter)를 추가 제공할 수 있다.

//class Person(val name: String) // 이런 유형의 클래스(코드가 없이 데이터만 저장하는 클래스)를 값 객체(value object)라 부른다.

// 자바에서는 필드와 접근자를 한데 묶어 프로퍼티(property)라고 부르며, 코틀린은 프로퍼티를 언어 기본 기능으로 제공한다.
// 코틀린 프로퍼티는 자바의 필드와 접근자 메소드를 완전히 대신한다.
class Person(
        val name: String, // val로 선언한 프로퍼티는 읽기 전용.
        var isMarried: Boolean // var로 선언한 프로퍼티는 변경 가능.
)

// 대부분의 프로퍼티에는 그 프로퍼티의 값을 저장하기 위한 필드가 있다.
// 이를 프로퍼티를 뒷받침하는 필드(backing field)라고 부른다.
// 하지만 원한다면 프로퍼티 값을 그때그때 계산(예를 들어 다른 프로퍼티들로부터 값을 계산할 수도 있다)할 수도 있다.
// 커스텀 게터를 만들면 그런 프로퍼티를 만들 수 있다.

class Rectangle(val height: Int, val width: Int) {
    // isSquare 프로퍼티에는 자체 값을 저장하는 필드가 필요 없다.
    // 이 프로퍼티에는 자체 구현을 제공하는 게터만 존재한다.
    // 클라이언트가 프로퍼티에 접근할 때마다 게터가 프로퍼티 값을 매번 다시 계산한다.
    val isSquare: Boolean
        get() { // 프로퍼티 게터 선언
            return height == width
        }
    // 파라미터가 없는 함수를 정의하는 방식과 커스텀 게터를 정의하는 방식 중 어느 쪽이 더 나을까?
    // 두 방식 모두 비슷하다. 구현이나 성능상 차이는 없다. 차이가 나는 부분은 가독성뿐이다.
    // 일반적으로 클래스의 특성(프로퍼티에는 특성이라는 뜻이 있다)을 정의하고 싶다면 프로퍼티로 그 특성을 정의해야 한다.
}

fun main(args: Array<String>) {
    val person = Person("Bob", true) // new 키워드를 사용하지 않고 생성자를 호출한다.
    println(person.name) // 프로퍼티 이름을 직접 사용해도 코틀린이 자동으로 게터를 호출해준다.
    println(person.isMarried)

    val rectangle = Rectangle(41, 43)
    println(rectangle.isSquare)
}