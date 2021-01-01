package step4

// 코틀린에서 클래스 계층을 정의하는 방식과 자바 방식을 비교한다. 그 후 코틀린의 가시성과 접근 변경자에 대해 살펴본다.

// 클래스 계층 정의
// 자바와 달리 코틀린 선언은 기본적으로 final이며 public이다. 게다가 중첩 클래스는 기본적으로 내부 클래스가 아니다. 즉 중첩 클래스에는 외부 클래스에 대한 참조가 없다.
// 코틀린 가시성/ 접근 변경자는 자바와 비슷하지만 아무것도 지정하지 않은 경우 기본 가시성은 다르다. 또한 코틀린에 새로 도입한 sealed 변경자에 대해 설명한다. sealed는 클래스 상속을 제한한다.

// 코틀린 인터페이스
// 코틀린 인터페이스는 자바8 인터페이스와 비슷하다.
// 코틀린 인터페이스 안에는 추상 메소드뿐만 아니라 구현이 있는 메소드도 정의할 수 있다(이는 자바 8의 디폴트 메소드와 비슷하다). 다만 인터페이스에는 아무런 상태(필드)도 들어갈 수 없다.
// 인터페이스는 interface로 정의한다.

// 이 코드는 click이라는 추상 메소드가 있는 인터페이스를 정의한다. 이 인터페이스를 구현하는 모든 비추상 클래스(또는 구체적 클래스)는 click에 대한 구현을 제공해야 한다.
/*
interface Clickable { fun click() }
*/

// 자바에서는 extends와 implements 키워드를 사용하지만, 코틀린에서는 클래스 이름 뒤에 콜론(:)을 붙이고 인터페이스와 클래스 이름을 적는 것으로 클래스 확장과 인터페이스 구현을 모두 처리한다.
// 자바와 마찬가지로 클래스는 인터페이스를 원하는 만큼 개수 제한 없이 마음대로 구현할 수 있지만, 클래스는 오직 하나만 확장할 수 있다.
// 자바의 @Override 애노테이션과 비슷한 override 변경자는 상위 클래스나 상위 인터페이스에 있는 프로퍼티나 메소드를 오버라이드한다는 표시다. 하지만 자바와 달리 코틀린에서는 override 변경자를 꼭 사용해야 한다.
// 상위 클래스에 있는 메소드와 시그니처가 같은 메소드를 우연히 하위 클래스에서 선언할 경우 컴파일이 안 되기 때문에 override를 붙이거나 메소드 이름을 바꿔야만 한다.
/*
class Button: Clickable {
    override fun click() = println("I was clicked")
}
*/

// 인터페이스 메소드도 디폴트 구현을 제공할 수 있다. 그런 경우 메소드 앞에 default를 붙여야하는 자바 8과 달리 코틀린에서는 메소드를 특별한 키워드로 꾸밀 필요가 없다.
// 이 인터페이스를 구현하는 클래스는 click에 대한 구현을 제공해야 한다. 반면 shoOff 메소드의 경우 새로운 동작을 정의할 수도 있고, 생략해서 디폴트 구현을 사용할 수도 있다.
interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

// 한 클래스에 위 두 메소드를 함께 구현하면 어떻게 될까? 어느 쪽 showOff 메소드가 선택 될까?
// 어느 쪽도 선택되지 않는다.
// 클래스가 구현하는 두 상위 인터페이스에 정의된 showOff 구현을 대체할 오버라이딩 메소드를 직접 제공하지 않으면 컴파일러가 오류를 발생한다.
// 코틀린 컴파일러는 두 메소드를 아우르는 구현을 하위 클래스에 직접 구현하게 강제한다.
// Button은 상속한 두 상위 타입의 showOff() 메소드를 호출하는 방식으로 showOff()를 구현한다. 상위 타입의 구현을 호출할 때는 자바와 마찬가지로 super를 사용한다. 하지만 구체적으로 타입을 지정하는 문법은 다르다.
// 자바에서는 Clickable.super.showOff() 처럼 super 앞에 기반 타입을 적지만, 코틀린에서는 super<Clickable>.showOff()처럼 꺽쇠 괄호 안에 기반 타입 이름을 지정한다.
class Button: Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff() { // 이름과 시그니처가 같은 멤버 메소드에 대해 둘 이상의 디폴트 구현이 있는 경우 인터페이스를 구현하는 하위 클래스에서 명시적으로 새로운 구현을 제공해야 한다.
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
    /*
    // 상속한 구현중 하나만 호출해도 된다면 다음과 같이 쓸 수도 있다.
    override fun showOff() = super<Clickable>.showOff()
    */
}

fun main(args: Array<String>) {
    val button = Button()
    button.showOff()
    button.setFocus(true)
    button.click()
}