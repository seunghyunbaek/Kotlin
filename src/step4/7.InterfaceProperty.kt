package step4

// 인터페이스에 선언된 프로퍼티 구현
// 코틀린에서는 인터페이스에 추상 프로퍼티 선언을 넣을 수 있다.
interface User2 {
    val nickname: String
}
// 이는 User 인터페이스를 구현하는 클래스가 nickname의 값을 얻을 수 있는 방법을 제공해야 한다는 뜻이다.
// 인터페이스에 있는 프로퍼티 선언에는 뒷받침하는 필드나 게터 등의 정보가 들어있지 않다. 사실 인터페이스는 아무 상태도 포함할 수 없으므로 상태를 저장할 필요가 있다면 인터페이스를 구현한 하위 클래스에서 상태 저장을 위한 프로퍼티 등을 만들어야 한다.

//이제 이 인터페이스를 구현하는 방법을 몇 가지 살펴보자.
class PrivateUser(override val nickname: String): User2 // 주 생성자에 있는 프로퍼티

class SubscribingUser(val email: String): User2 {
    override val nickname: String
        get() = email.substringBefore("@") // 커스텀 게터 // nickname 호출시 매번 substringBefore 를 호출해 계산하는 커스텀 게터
}

class FacebookUser(val accountId: Int): User2 {
    override val nickname = getFacebookName(accountId) // 프로퍼티 초기화 식 // 객체 초기화 시 계산한 데이터를 뒷받침하는 필드에 저장했다가 불러오는 방식을 활용한다.
}

fun getFacebookName(accountId: Int) = "solob"

// 인터페이스에는 추상 프로퍼티뿐 아니라 게터와 세터가 있는 프로퍼티를 선언할 수도 있다.
// 물론 그런 게터와 세터는 뒷받침하는 필드를 참조할 수 없다(뒷받침하는 필드가 있다면 인터페이스에 상태를 추가하는 셈인데 인터페이스는 상태를 저장할 수 없다).
interface User3 {
    val email: String
    val nickname: String
        get() = email.substringBefore("@") // 프로퍼티에 뒷받침하는 필드가 없다. 대신 매번 결과를 계산해 돌려준다.
}
// 하위 클래스는 추상 프로퍼티인 email을 반드시 오버라이드해야 한다. 반면 nickname은 오버라이드하지 않고 상속할 수 있다.
// 인터페이스에 선언된 프로퍼티와 달리 클래스에 구현된 프로퍼티는 뒷받침하는 필드를 원하는대로 사용할 수 있다.

// 게터와 세터에서 뒷받침하는 필드에 접근
// 지금까지 프로퍼티의 두 가지 유형(값을 저장하는 프로퍼티와 커스텀 접근자에서 매번 값을 계산하는 프로퍼티)에 대해 살펴봤다.
// 이제 이 두 유형을 조합해서 어떤 값을 저장하되 그 값을 변경하거나 읽을 때마다 정해진 로직을 수행하는 유형의 프로퍼티를 만드는 방법을 살펴보자.

class User4(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
                Address was changed for $name: 
                "$field" -> "$value".""".trimIndent()) // 뒷받침하는 필드 값 읽기
            field = value // 뒷받침하는 필드 값 변경하기
        }
}
// 접근자의 본문에는 field 라는 특별한 식별자를 통해 뒷받침하는 필드에 접근할 수 있다.
// 게터에서는 field 값을 읽을 수만 있고, 세터에서는 field 값을 읽거나 쓸 수 있다.
// 변경 가능 프로퍼티의 게터와 세터중 한쪽만 직접 정의해도 된다는 점을 기억하자.

// 뒷받침하는 필드가 있는 프로퍼티와 없는 프로퍼티에 어떤 차이가 있는지 궁금할 것이다.
// 클래스의 프로퍼티를 사용하는 쪽에서 프로퍼티를 읽는 방법이나 쓰는 방법은 뒷받침하는 필드의 유무와는 관계없다.
// 컴파일러는 디폴트 접근자 구현을 사용하건 직접 게터나 세터를 정의하건 관계없이 게터나 세터에서 field를 사용하는 프로퍼티에 대해 뒷받침하는 필드를 생성해준다.
// 다만 field를 사용하지 않는 커스텀 접근자 구현을 정의한다면 뒷받침하는 필드는 존재하지 않는다(프로퍼티가 val인 경우에는 게터에 field가 없으면 안되지만, var인 경우에는 게터나 세터 모두에 field가 없어야 한다).

// 때로 접근자의 기본 구현을 바꿀 필요는 없지만 가시성을 바꿀 필요가 있는 때가 있다.
// 접근자의 가시성 변경
// 접근자의 가시성은 기본적으로는 프로퍼티의 가시성과 같다.하지만 원한다면 get이나 set앞에 가시성 변경자를추가해서 접근자의 가시성을 변경할 수 있다.
class LengthCounter { // 이 클래스는 자신에게 추가된 모든 단어의 길이를 합산한다.
    var counter: Int = 0
        private set // 이 클래스 밖에서 이 프로퍼티의 값을 바꿀 수 없다.

    fun addWord(word: String) {
        counter += word.length
    }
}
// 전체 길이를 저장하는 프로퍼티는 클라이언트에게 제공하는 API의 일부분이므로 public으로 외부에 공개된다. 하지만 외부 코드에서 단어 길이의 합을 마음대로 바꾸지 못하게 이 클래스 내부에서만 길이를 변경하게 만들고 싶다.
// 그래서 기본 가시성을 가진 게터를 컴파일러가 생성하게 내버려두는 대신 세터의 가시성을 private으로 지정한다.

// 프로퍼티에 대해 나중에 다룰 내용
// lateinit 변경자를 널이 될 수 없는 프로퍼티에 지정하면 프로퍼티를 생성자가 호출된 다음에 초기화한다는 뜻이다.
// 요청이 들어오면 비로소 초기화되는 지연 초기화(lazy initialized) 프로퍼티는 더 일반적인 위임 프로퍼티(delegated property)으 ㅣ일종이다.
// 자바 프레임워크와의 호환성을 위해 자바의 특징을 코틀린에서 에뮬레이션하는 애노테이션을 활용할 수 있다. 예를 들어 @JvmField 애노테이션을 프로퍼티에 붙이면 접근자가 없는 public 필드를 노출시켜준다.
fun main(args: Array<String>) {
    println(PrivateUser("solob@kotlinlang.org").nickname)
    println(SubscribingUser("solob@kotlinlang.org").nickname)

    val user = User4("solob")
    user.address = "Elsenheimerstrasse 47, 80687 Muenchen" // 코틀린에서 프로퍼티의 값을 바꿀 때는 user.address="new value"처럼 필드 설정 구문을 사용한다. 이 구문은 내부적으로 address의 세터를 호출한다.

    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    println(lengthCounter.counter)
}