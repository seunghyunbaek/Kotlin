package step4

// 어떤 클래스가 데이터를 저장하는 역할만을 수행한다면 toString, equals, hashCode 를 반드시 오버라이드해야 한다.
// 코틀린은 더 편하다. 이제는 이런 메소드를 IDE를 통해 생성할 필요도 없다.
// data 라는 변경자를 클래스 앞에 붙이면 필요한 메소드를 컴파일러가 자동으로 만들어준다. data 변경자가 붙은 클래스를 데이터 클래스라고 부른다.
data class Client(val name: String, val postalCode: Int)
// 이제 Client 클래스는 자바에서 요구하는 모든 메소드를 포함한다.
    // 인스턴스 간 비교를 위한 equals
    // HashMap과 같은 해시 기반 컨테이너에서 키로 사용할 수 있는 hashCode
    // 클래스의 각 필드를 선언 순서대로 표시하는 문자열 표현을 만들어주는 toString
// equals와 hashCode는 주 생성자에 나열된 모든 프로퍼티를 고려해 만들어진다.
// 생성된 equals 메소드는 모든 프러피티 값의 동등성을 확인한다.
// hashCode 메소드는 모든 프로퍼티의 해시 값을 바탕으로 계산한 해시 값을 반환한다. 이때 주 생성자 밖에 정의된 프로퍼티는 equals나 hashaCode를 계산할 때 고려의 대상이 아니라는 사실에 유의하자.
// 코틀린 컴파일러는 data클래스에게 세 메소드뿐 아니라 몇 가지 유용한 메소드를 더 생성해준다.

// 데이터 클래스와 불변성: copy() 메소드
// 데이터 클래스의 프로퍼티가 꼭 val일 필요는 없다. 하지만 데이터 클래스의 모든 프로퍼티를 읽기 전용으로 만들어서 데이터 클래스를 불변(immutable) 클래스로 만들라고 권장한다.
// HashMap 등의 컨테이너에 데이터 클래스 객체를 담는 경우엔 불변성이 필수적이다. 데이터 클래스 객체를 키로 하는 값을 컨테이너에 담은 다음에 키로 쓰인 데이터 객체의 프로퍼티를 변경하면 컨테이너 상태가 잘못될 수 있다. 게다가 불변 객체를 사용하면 프로그램에 대해 훨씬 쉽게 추론할 수 있다.
// 특히 다중스레드 프로그램의 경우 이런 성질은 더 중요하다. 불변 객체를 주로 사용하는 프로그램에서는 스레드가 사용 중인 데이터를 다른 스레드가 변경할 수 없으므로 스레드를 동기화해야 할 필요가 줄어든다.

// 데이터 클래스 인스턴스를 불면 객체로 더 쉽게 활용할 수 있게 코틀린 컴파일러는 한 가지 편의 메소드를 제공한다. 그 메소드는 객체를 복사(copy)하면서 일부 프로퍼티를 바꿀 수 있게 해주는 copy 메소드다.
// 객체를 메모리상에서 직접 바꾸는 대신 복사본을 만드는 편이 더 낫다. 복사본은 원본과 다른 생명주기를 가지며, 복사를 하면서 일부 프로퍼티 값을 바꾸거나 복사본을 제거해도 프로그램에서 원본을 참조하는 다른 부분에 전혀 영향을 끼치지 않는다.
// Client의 copy를 직접 구현하면 다음과 같을 것이다.
class Client2(val name: String, val postalCode: Int) {
    /*...*/
    fun copy(name: String = this.name, postalCode: Int = this.postalCode) = Client2(name, postalCode)
}
// 지금까지 data 변경자를 통해 값 객체를 더 편리하게 사용하는 방법을 살펴봤다.
// 이제는 IDE가 생성해주는 코드를 사용하지 않고도 위임을 쉽게 사용할 수 있게 해주는 코틀린 기능인 클래스 위임(class delegation)에 대해 살펴보자.
// 대규모 객체지향 시스템을 설계할 때 시스템을 취약하게 만드는 문제는 보통 구현 상속(implementation inheritance)에 의해 발생한다.
// 하위 클래스가 상위 클래스의 메소드 중 일부를 오버라이드하면 하위 클래스는 상위 클래스의 세부 구현 사항에 의존하게 된다.
// 시스템이 변함에 따라 상위 클래스의 구현이 바뀌거나 상위 클래스에 새로운 메소드가 추가된다. 그 과정에서 하위 클래스가 상위 클래스에 대해 갖고 있던 가정이 깨져서 코드가 정상적으로 작동하지 못하는 경우가 생길 수 있다.
// 코틀린을 설계하면서 우리는 이런 문제를 인식하고 기본적으로 클래스를 final로 취급하기로 결정했다. 모든 클래스를 기본적으로 final로 취급하면 상속을 염두에 두고 open 변경자로 열어둔 클래스만 확장할 수 있다.
// 열린 상위 클래스의 소스코드를 변경할 때는 open 변경자를 보고 해당 클래스를 다른 클래스가 상속하리라 예상할 수 있으므로, 변경 시 하위 클래스를 깨지 않기 위해 좀 더 조심할 수 있다.
// 하지만 종종 상속을 허용하지 않은 클래스에 새로운 동작을 추가해야 할 때가 있다. 이럴 때 사용하는 일반적인 방법이 데코레이터(Decorator) 패턴이다.
// 이 패턴의 핵심은 상속을 허용하지 않는 클래스(기존 클래스) 대신 사용할 수 있는 새로운 클래스(데코레이터)를 만들되 기존 클래스와 같은 인터페이스를 데코레이터가 제공하게 만들고, 기존 클래스를 데코레이터 내부에 필드로 유지하는 것이다.
// 이때 새로 정의해야 하는 기능은 데코레이터의 메소드에 정의하고(물론 이때 기존 클래스의 메소드나 필드를 활용할 수도 있다) 기존 기능이 그대로 필요한 부분은 데코레이터의 메소드가 기존 클래스의 메소드에게 요청을 전달(forwarding)한다.
// 이런 접근 방법의 단점은 준비 코드가 상당히 많이 필요하다는 점이다. 예를 들어 Collection 같이 비교적 단순한 인터페이스를 구현하면서 아무 동작도 변경하지 않는 데코레이터를 만들 때조차도 다음과 같이 복잡한 코드를 작성해야 한다.
class DelegatingCollection<T>: Collection<T> {
    private val innerList = arrayListOf<T>()

    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)
}
// 이런 위임을 언어가 제공하는 일급 시민 기능으로 지원한다는 점이 코틀린의 장점이다.
// 인터페이스를 구현할 때 by 키워드를 통해 그 인터페이스에 대한 구현을 다른 객체에 위임 중이라는 사실을 명시할 수 있다.
// 다음은 앞의 예제를 위임을 사용해 재작성한 코드다.
class DelegatingCollection2<T>(
        innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList { }
// 클래스 안에 있던 모든 메소드 정의가 없어졌다. 컴파일러가 그런 전달 메소드를 자동으로 생성하며 자동 생성한 코드의 구현은 DelegatingCollection에 있던 구현과 비슷하다.
// 그런 단순한 코드 중 관심을 가질 만한 부분은 거의 없기 때문에 컴파일러가 자동으로 해줄 수 있는 작업을 굳이 직접 해야 할 이유는 없다.
// 메소드 중 일부의 동작을 변경하고 싶은 경우 메소드를 오버라이드하면 컴파일러가 생성한 메소드 대신 오버라이드한 메소드가 쓰인다.
// 이 기법을 이용해서 원소를 추가하려고 시도한 횟수를 기록하는 컬렉션을 구현해보자.
// 예를 들어 중복을 제거하는 프로세스를 설계하는 중이라면 원소 추가 횟수를 기록하는 컬렉션을 통해 최종 컬렉션 크기와 원소 추가 시도 횟수 사이의 비율을 살펴봄으로써 중복 제거 프로세스의 효율성을 판단할 수 있다.
class CountingSet<T> (
        val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet { // MutableCollection의 구현을 innerSet에게 위임한다.
    var objectsAdded = 0
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}
// add와 addAll을 오버라이드해서 카운터를 증가시키고 MutableCollection 인터페이스의 나머지 메소드는 내부 컨테이너(innerSet)에게 위임한다.
// 이때 CountingSet에 MutableCollection의 구현 방식에 대한 의존관계가 생기지 않는다는 점이 중요하다.
// 예를 들어 내부 컨테이너가 addAll을 처리할 때 루프를 돌면서 add를 호출할 수도 있지만, 최적화를 위해 다른 방식을 택할 수도 있다.
// 클라이언트 코드가 CountingSet의 코드를 호출할 때 발생하는 일은 CountingSet 안에서 마음대로 제어할 수 있지만, CountingSet 코드는 위임 대상 내부 클래스인 MutableColelction에 문서화된 API를 활용한다.
// 그러므로 내부 클래스 MutableCollection이 문서화된 API를 변경하지 않는 한 CountingSet 코드가 계속 잘 작동할 것임을 확신할 수 있다.

fun main(args: Array<String>) {
    val lee = Client2("이계영", 4122)
    println(lee.copy(postalCode = 4000))
    println(lee)

    val cset = CountingSet<Int>()
    cset.addAll(listOf(1, 1, 2))
    println("${cset.objectsAdded} objects were added, ${cset.size} remain")
}

// 코틀린 컴파일러가 유용한 메소드를 생성해주는 방식에 대한 설명을 마쳤다.
// 이제 코틀린 클래스에 대해 남은 마지막 중요한 요소인 object 키워드와 언제 그 키워드를 활용할 수 있는지 살펴보자.