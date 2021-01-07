package step4

import java.awt.SystemColor.window
import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.io.File

// object 키워드 : 클래스 선언과 인스턴스 생성
// 코틀린에서는 object 키워드를 다양한 상황에서 사용하지만 모든 경우 클래스를 정의하면서 동시에 인스턴스(객체)를 생성한다는 공통점이 있다.
// object 키워드를 사용하는 여러 상황을 살펴보자.
    // 객체 선언(object declaration)은 싱글턴을 정의하는 방법 중 하나다.
    // 동반 객체(companion object)는 인스턴스 메소드는 아니지만 어떤 클래스와 관련 있는 메소드와 팩토리 메소드를 담을 때 쓰인다. 동반 객체 메소드에 접근할 때는 동반 객체가 포함된 클래스의 이름을 사용할 수 있다.
    // 객체 식은 자바의 무명 내부 클래스(anonymous inner class)대신 쓰인다.
// 지금부터 이런 코틀린의 특성에 대해 자세히 설명한다.

// 객체 선언 : 싱글턴을 쉽게 만들기
// 객체지향 시스템을 설게하다 보면 인스턴스가 하나만 필요한 클래스가 유용한 경우가 많다.
// 자바에서는 보통 클래스의 생성자를 private으로 제한하고 정적인 필드에 그 클래스의 유일한 객체를 저장하는 싱글턴 패턴(singleton pattern)을 통해 이를 구현한다.
// 코틀린은 객체 선언 기능을 통해 싱글턴을 언어에서 기본 지원한다. 객체 선언은 클래스 선언과 그 클래스에 속한 단일 인스턴스의 선언을 합친 선언이다.
// 예를 들어 객체 선언을 사용해 회사 급여 대장을 만들 수 있다. 한 회사에 여러 급여 대장이 필요하지는 않을 테니 싱글턴을 쓰는게 정당해 보인다.
/*
object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) { ... }
    }
}
*/
// 객체 선언은 object 키워드로 시작한다. 객체 선언은 클래스를 정의하고 그 클래스의 인스턴스를 만들어서 변수에 저장하는 모든 작업을 단 한 문장으로 처리한다.
// 클래스와 마찬가지로 객체 선언 안에도 프로퍼티, 메소드, 초기화 블록 등이 들어갈 수 있지만, 생성자는(주 생성자와 부 생성자 모두) 객체 선언에 쓸 수 없다.
// 일반 클래스 인스턴스와 달리 싱글턴 객체는 객체 선언문이 있는 위치에서 생성자 호출 없이 즉시 만들어진다. 따라서 객체 선언에는 생성자 정의가 필요 없다.
// 변수와 마찬가지로 객체 선언에 사용한 이름 뒤에 마침표(.)를 붙이면 객체에 속한 메소드나 프로퍼티에 접근할 수 있다.
/*
Payroll.allEmployees.add(Person(...))
Payroll.calculateSalary()
*/
// 객체 선언도 클래스나 인터페이스를 상속할 수 있다.
// 프레임워크를 사용하기 위해 특정 인터페이스를 구현해야 하는데, 그 구현 내부에 다른 상태가 필요하지 않은 경우에 이런 기능이 유용하다.
// 예를 들어 java.util.Comparator 인터페이스를 살펴보자.
// Comparator 구현은 두 객체를 인자로 받아 그중 어느 객체가 더 큰지를 알려주는 정수를 반환한다.
// Comparator 안에는 데이터를 저장할 필요가 없다. 따라서 어떤 클래스에 속한 객체를 비교할 때 사용하는 Comparator는 보통 클래스마다 단 하나씩만 있으면 된다.
// 따라서 Comparator 인스턴스를 만드는 방법으로는 객체 선언이 가장 좋은 방법이다.
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

// 싱글턴 패턴과 의존관계 주입
// 싱글턴 패턴과 마찬가지 이유로 대규모 소프트웨어 시스템에서는 객체 선언이 항상 적합하지는 않다. 의존관계가 별로 많지 않은 소규모 소프트웨어에서는 싱글턴이나 객체 선언이 유용하지만,
// 시스템을 구현하는 다양한 구성 요소와 상호작용하는 대규모 컴포넌트에는 싱글턴이 적합하지 않다. 이유는 객체 생성을 제어할 방법이 없고 생성자 파라미터를 지정할 수 없어서다.
// 생성을 제어할 수 없고 생성자 파라미터를 지정할 수 없으므로 단위 테스트를 하거나 소프트웨어 시스템의 설정이 달라질 때 객체를 대체하거나 객체의 의존관계를 바꿀 수 없다.
// 따라서 그런 기능이 필요하다면 자바와 마찬가지로 의존관계 주입 프레임워크(예: 구글 주스(Guice))와 코틀린 클래스를 함께 사용해야 한다.

// 클래스 안에 객체를 선언할 수도 있다. 그런 객체도 인스턴스는 단 하나뿐이다(바깥 클래스의 인스턴스마다 중첩 객체 선언에 해당하는 인스턴스가 하나씩 따로 생기는 것이 아니다).
// 예를 들어 어떤 클래스의 인스턴스를 비교하는 Comparator를 클래스 내부에 정의하는게 더 바람직하다.
data class Person(val name: String) {
    object NameComparator: Comparator<Person> {
        override fun compare(person1: Person, person2: Person): Int = person1.name.compareTo(person2.name)
    }
}
// 코틀린 객체 선언은 유일한 인스턴스에 대한 정적인 필드가 있는 자바 클래스로 컴파일된다. 이때 인스턴스 필드의 이름은 항상 INSTANCE다.
// 싱글턴 패턴을 자바에서 구현해도 비슷한 필드가 필요하다. 자바코드에서 코틀린 싱글턴 객체를 사용하려면 정적인 INSTANCE 필드를 통하면 된다.
/* 자바
CaseInsensitiveFileComparator.INSTACNE.compare(file1, file2);
INSTANCE 필드의 타입은 CaseInsensitiveFileComparator 다.
*/


// 이제 클래스 안에 중첩된 객체 중에서도 독특한 객체를 살펴보자. 그 객체는 바로 동반 객체(companion object) 다.
// 동반 객체 : 팩토리 메소드와 정적 멤버가 들어갈 장소
// 코틀린 클래스 안에는 정적인 멤버가 없다. 코틀린 언어는 자바 static 키워드를 지원하지 않는다.
// 그 대신 코틀린에서는 패키지 수준의 최상위 함수(자바의 정적 메소드 역할을 거의 대신 할 수 있다)와 객체 선언(자바의 정적 메소드 역할 중 코틀린 최상위 함수가 대신할 수 없는 역할이나 정적 필드를 대신할 수 있다)을 활용한다.
// 대부분의 경우 최상위 함수를 활용하는 편을 더 권장한다. 하지만 최상위 함수는 private으로 표시된 클래스 비공개 멤버에 접근할 수 없다. 그래서 클래스의 인스턴스와 관계없이 호출해야 하지만, 클래스 내부 정보에 접근해야 하는 함수가 필요할 때는 클래스에 중첩된 객체 선언의 멤버 함수로 정의해야 한다.
// 그런 함수의 대표적인 예로 팩토리 메소드를 들 수 있다.
/*
class Foo {
    private val foo
    object { // foo 호출 가능 }
}

// 클래스 밖 최상위 함수는 비공개 멤버를 사용할 수 없다.
fun callFoo() { Foo().foo // foo 호출 불가능 }
*/
// 클래스 안에 정의된 객체 중 하나에 companion 이라는 특별한 표시를 붙이면 그 클래스의 동반 객체로 만들 수 있다. 동반 객체의 프로퍼티나 메소드에 접근하려면 그 동반 객체가 정의된 클래스의 이름을 사용한다.
// 이때 객체의 이름을 따로 지정할 필요가 없다. 그 결과 동반 객체의 멤버를 사용하는 구문은 자바의 정적 메소드 호출이나 정적 필드 사용구문과 같아진다.
class A {
    private constructor() {}

    companion object {
        fun bar() { println("Companion objet called")}
    }

    object _A {
        fun bar() { println("Object called")}
    }
}
// private 생성자를 호출하기 좋은 위치는 바로 동반 객체가 private 생성자를 호출하기 좋은 위치다.
// 동반 객체는 자신을 둘러싼 클래스의 모든 private 멤버에 접근할 수 있다. 따라서 동반 객체는 바깥쪽 클래스의 private 생성자도 호출할 수 있다.

// 이제 예제로 부 생성자가 2개 있는 클래스를 살펴보고, 다시 그 클래스를 동반 객체 안에서 팩토리 클래스를 정의하는 방식으로 변경해보자.
// 부 생성자가 여럿 있는 클래스 정의하기
class User5 {
    val nickname: String
    constructor(email: String) { // 부 생성자
        nickname = email.substringBefore("@")
    }
    constructor(facebookAccountId: Int) { // 부 생성자
        nickname = getFacebookName(facebookAccountId)
    }
}
// 이런 로직을 표현하는 더 융요한 방법으로 클래스의 인스턴스를 생성하는 팩토리 메소드가 있다. 생성자를 통해 User 인스턴스를 만들 수 없고 팩토리 메소드를 통과해야만 한다.

class User6 private constructor(val nickname: String) { // 주 생성자를 비공개로 만든다.
    companion object {
        fun newSubstringUser(email: String) = User6(email.substringBefore("@"))
        fun newFacebookUser(accountId: Int) = User6(getFacebookName(accountId)) // 페이스북 사용자 ID로 사용자를 만드는 팩토리 메소드
    }
}
// 팩토리 메소드는 매우 유용하다. 이 예제처럼 목적에 따라 팩토리 메소드 이름을 정할 수 있다. 게다가 팩토리 메소드는 그 팩토리 메소드가 선언된 클래스의 하위 클래스 객체를 반환할 수도 있다.
// 예를 들어 SubscribingUser와 FacebookUser 클래스가 따로 존재한다면 그때그때 필요에 따라 적당한 클래스의 객체를 반환할 수 있다. 또 팩토리 메소드는 생성할 필요가 없는 객체를 생성하지 않을 수도 있다.
// 예를 들어 이메일 주소별로 유일한 User 인스턴스를 만드는 경우 팩토리 메소드가 이미 존재하는 인스턴스에 해당하는 이메일 주소를 전달받으면 새 인스턴스를 만들지 않고 캐시에 있는 기존 인스턴스를 반환할 수 있다.
// 하지만 클래스를 확장해야만 하는 경우에는 동반 객체 멤버를 하위 클래스에서 오버라이드할 수 없으므로 여러 생성자를 사용하는 편이 더 나은 해법이다.

// 동반 객체를 일반 객체처럼 사용
// 동반 객체는 클래스안에 정의된 일반 객체다. 따라서 동반 객체에 이름을 붙이거나 동반 객체가 인터페이스를 상속하거나, 동반 객체 안에 확장 함수와 프로퍼티를 정의할 수 있다.
// 동반 객체에 특별히 이름을 지정하지 않으면 동반 객체 이름은 자동으로 Companion이 된다.

// 동반 객체에서 인터페이스 구현
// 다른 객체 선언과 마찬가지로 동반 객체도 인터페이스를 구현할 수 있다. 잠시 후 보겠지만 인터페이스를 구현하는 동반 객체를 참조할 때 객체를 둘러싼 클래스의 이름을 바로 사용할 수 있다.

// 코틀린 동반 객체와 정적 멤버
// 클래스의 동반 객체는 일반 객체와 비슷한 방식으로, 클래스에 정의된 인스턴스를 가리키는 정적 필드로 컴파일된다.
// 때로 자바에서 사용하기 위해 코틀린 클래스의 멤버를 정적인 멤버로 만들어야 할 필요가 있다. 그런 경우 @JvmStatic 애노테이션을 코틀린 멤버에 붙이면 된다.
// 정적 필드가 필요하다면 @JvmField 애노테이션을 최상위 프로퍼티나 객체에서 선언된 프로퍼티 앞에 붙인다.
// 이 기능은 자바와의 상호운용성을 위해 존재하며, 코틀린 핵심 언어가 제공하는 기능은 아니다.
// 코틀린에서도 자바의 정적 필드나 메소드를 사용할 수 있다. 그런 경우 자바와 똑같은 구문을 사용한다.

// 동반 객체 확장
// 자바의 정적 메소드나 코틀린의 동반 객체 메소드처럼 기존 클래스에 대해 호출할 수 있는 새로운 함수를 정의하고 싶다면 어떻게 해야 할까?
// 클래스에 동반 객체가 있으면 그 객체 안에 함수를 정의함으로써 클래스에 대해 호출할 수 있는 확장 함수를 만들 수 있다. 더 구체적으로 설명해보자.
// C 라는 클래스 안에 동반 객체가 있고 그 동반 객체(C, Companion) 안에 func를 정의하면 외부에서는 func()를 C.func()로 호출할 수 있다.
// 동반 객체에 대한 확장 함수를 작성할 수 있으려면 원래 클래스에 반드시 동반 객체를 꼭 선언해야 한다는 점에 주의하자. 설령 빈 객체라도 꼭 있어야 한다.

// 객체 식(object expression) : 무명 내부 클래스를 다른 방식으로 작성
// object 키워드를 싱글턴과 같은 객체를 정의하고 그 객체에 이름을 붙일 때만 사용하지는 않는다. 무명 객체(anonymous object)를 정의할 때도 object 키워드를 쓴다. 무명 객체는 자바의 무명 내부 클래스를 대신한다.
// 예를 들어 자바에서 흔히 무명 내부 클래스로 구현하는 이벤트 리스너(event listener)를 코틀린에서 구현해보자.
/*
window.addMouseListener(
    object : MouseAdapter() { // MouseAdaper를 확장하는 무명 객체를 선언한다.
        override fun mouseReleased(e: MouseEvent?) {
            super.mouseReleased(e)
        } // MouseAdapter의 메소드를 오버라이드 한다.
        override fun mouseMoved(e: MouseEvent?) {
            super.mouseMoved(e)
        }
    }
)
*/
// 사용한 구문은 객체 선언에서와 같다. 한 가지 유일한 차이는 객체 이름이 빠졌다는 점이다.
// 객체 식은 클래스를 정의하고 그 클래스에 속한 인스턴스를 생성하지만, 그 클래스나 인스턴스에 이름을 붙이지 않는다.
// 이런 경우 보통 함수를 호출하면서 인자로 무명 객체를 넘기기 때문에 클래스와 인스턴스 모두 이름이 필요하지 않다. 하지만 객체에 이름을 붙여야 한다면 변수에 무명 객체를 대입하면 된다.
val listener = object: MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) {
        super.mouseClicked(e)
    }

}
// 한 인터페이스만 구현하거나 한 클래스만 확장할 수 있는 자바의 무명 내부 클래스와 달리 코틀린 무명 클래스는 여러 인터페이스를 구현하거나 클래스를 확장하면서 인터페이스를 구현할 수 있다.
// 객체 선언과 달리 무명 객체는 싱글턴이 아니다. 객체 식이 쓰일 때마다 새로운 인스턴스가 생성된다.
// 자바의 무명 클래스와 같이 객체 식 안의 코드는 그 식이 포함된 함수의 변수에 접근할 수 있다. 따라서 객체 식 안에서 그 변수의 값을 변경할 수 있다.
fun countClicks(window: Window) {
    var clickCount = 0 // 로컬 변수를 정의한다.

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            clickCount++ // 로컬 변수의 값을 변경한다.
        }
    })
}
// 객체 식은 무명 객체 안에서 여러 메소드를 오버라이드해야 하는 경우에 훨씬 더 유용하다.
// 메소드가 하나뿐인 인터페이스(Runnable 등의 인터페이스)를 구현해야 한다면 코틀린의 SAM 변환(함수 리터럴(function literal)을 변환해 SAM으로 만듦)지원을 활용하는 편이 낫다.
// SAM 변환을 사용하려면 무명 객체 대신 함수 리터럴(람다(lambda))을 사용해야 한다. 람다와 SAM에 대해서는 step5에서 배운다.

// SAM은 추상 메소드가 하나만 있는(Single Abstract Method) 인터페이스라는 뜻이다. 자바에서 Runnable, Comparator, Callable, ActionLister 등 상당수의인터페이스가 SAM이다.
// 다른 말로 함수형 인터페이스(functional interface)라고도 부른다. 자바 8에 도입된 자바 람다를 SAM 인터페이스를 구현하는 무명 클래스 대신 사용할 수 있다.

fun main(args: Array<String>) {
    println(CaseInsensitiveFileComparator.compare(File("/User"), File("")))

    // 일반 객체(클래스 인스턴스)를 사용할 수 있는 곳에서는 항상 싱글턴 객체를 사용할 수 있다. 예를 들어 이 객체를 Comparator를 인자로 받는 함수에게 인자로 넘길 수 있다
    // 예를 들어 이 객체를 Comparator를 인자로 받는 함수에게 인자로 넘길 수 있다.
    val files = listOf(File("/z"), File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator)) // 전달받은 Comparator에 따라 리스트를 정렬하는 sortedWith 함수를 사용한다.

    val persons = listOf(Person("Bob"), Person("Alice"))
    println(persons.sortedWith(Person.NameComparator))

    A.bar()
    A._A.bar()

    val subscribingUser = User6.newSubstringUser("solob@gmail.com")
    val facebookUser = User6.newFacebookUser(50106)
    println(subscribingUser.nickname)
    println(facebookUser.nickname)
}