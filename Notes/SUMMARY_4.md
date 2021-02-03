# 클래스, 객체, 인터페이스
자바와 달리 코틀린 선언은 기본적으로 final이며 public이다. 게다가 중첩 클래스는 기본적으로 내부 클래스가 아니다.

즉, 코틀린 중첩 클래스에는 외부 클래스에 대한 참조가 없다.

코틀린 컴파일러는 번잡스러움을 피하기 위해 유용한 메소드를 자동으로 만들어준다.  
클래스를 data로 선언하면 컴파일러가 일부 표준 메소드를 생성해준다.

싱글턴 클래스, 동반 객체(companion object), 객체 식(object expression)을 표현할 때 obejct 키워드를 쓴다.

## 클래스 계층 정의
- 계층 정의 방식
- 가시성과 접근 변경자
- sealed 변경자

#### 코틀린 인터페이스
클래스는 calss, 인터페이스는 interface로 정의한다.  

코틀린 인터페이스 안에는 추상 메소드뿐 아니라 구현이 있는 메소드도 정의할 수 있다. 다만 인터페이스에는 아무런 상태(필드)도 들어갈 수 없다.
```kotlin
interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}


class Button: Clickable, Focusable {
    override fun click() = println("I was Clicked")
    
    // 이름과 시그니처가 같은 멤버 메소드에 대해 둘 이상의 디폴트 구현이 있는 경우 하위 클래스에서 명시적으로 새로운 구현을 제공해야 한다.
    override fun showOff() { 
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}
```
코틀린에서는 클래스 이름 뒤에 콜론(:)을 붙이고 인터페이스와 클래스 이름을 적는 것으로 클래스 확장과 인터페이스 구현을 모두 처리한다.

인터페이스는 원하는 만큼 개수 제한 없이 구현할 수 있지만, 클래스는 오직 하나만 확장할 수 있다.

override 변경자는 상위 클래스나 상위 인터페이스에 있는 프로퍼티나 메소드를 오버라이드한다는 표시다.  
코틀린에서는 override 변경자를 꼭 사용해야 한다. override 변경자는 실수로 상위 클래스의 메소드를 오버라이드 하는 경우를 방지해준다.

#### open, final, abstract 변경자 : 기본적으로 final
기본적으로 상속이 가능하면 편리한 경우도 많지만 문제가 생기는 경우가 있다.  
취약한 기반 클래스(fragile base calss)라는 문제는 하위 클래스가 기반 클래스에 대해 가졌던 가정이 기반 클래스를 변경함으로써 깨져버리는 경우에 생긴다.

코틀린의 클래스와 메소드는 기본적으로 final로 상속을 허용하려면 open 변경자를 붙여야 한다. 그와 더불어 오버라이드를 허용하고 싶은 메소드나 프로퍼티의 앞에도 open 변경자를 붙여야 한다.
```kotlin
open class RadioButton: Clickable {
    fun disable() {} // 이 함수는 final이다. 하위 클래스가 오버라이드 할 수 없다.
    open fun animate() {} // 이 함수는 열려있다. 하위 클래스에서 오버라이드 해도 된다.
    override fun click() {} // 열려 있는 메소드를 오버라이드 한다. 오버라이드한 메소드는 기본적으로 열려있다.

}
```

코틀린에서도 클래스를 abstract로 선언할 수 있다.  
abstract로 선언한 추상 클래스는 인스턴스화 할 수 없다.  
추상 클래스에는 구현이 없는 추상 멤버가 있기 때문에 하위 클래스에서 그 추상 멤버를 오버라이드해야만 하는 게 보통이다. 

추상 멤버는 항상 열려있다. 따라서 추상 멤버 앞에 open 변경자를 명시할 필요가 없다.
```kotlin
abstract class Animated {
    abstract fun animate() // 추상 함수. 구현이 없다. 하위 클래스에서 반드시 오버라이드 해야 한다.
    open fun stopAnimating() {} // 추상 클래스에 속했더라도 비추상 함수는 기본적으로 final이다. 원한다면 open으로 오버라이드를 허용할 수 있다.  
    fun animateTwice() {} // 이 함수는 final이다.
}
``` 

인터페이스 멤버의 경우 final, open, abstract를 사용하지 않는다.  
인터페이스 멤버는 항상 열려 있으며 final로 변경할 수 없다. 인터페이스 멤버에게 본문이 없으면 자동으로 추상 멤버가 되지만, 따로 멤버 선언 앞에 abstract 키워드를 덧붙일 필요가 없다.


#### 가시성 변경자: 기본적으로 공개
가시성 변경자(visibility modifier)는 코드 기반에 있는 선언에 대한 클래스 외부 접근을 제어한다.  
어떤 클래스의 구현에 대한 접근을 제한함으로써 그 클래스에 의존하는 외부 코드를 깨지 않고도 클래스 내부 구현을 변경할 수 있다.

코틀린의 기본 가시성은 모두 공개(public)다.

자바의 기본 가시성인 패키지 전용(package-private)은 코틀린에 없다.  
코틀린은 패키지를 네임스페이스(namespace)를 관리하기 위한 용도로만 사용한다. 그래서 패키지를 가시성 제어에 사용하지 않는다. 

패키지 전용에 대한 대안으로 코틀린에는 internal이라는 새로운 가기성 변경자를 도입했다.  
internal은 "모듈 내부에서만 볼 수 있음" 이라는 뜻이다. 모듈(module)은 한 번에 한꺼번에 컴파일되는 코틀린 파일들을 의미한다.

모듈 내부 가시성은 여러분의 모듈의 구현에대해 진정한 캡슐화를 제공한다는 장점이 있다.  
자바에서는 패키지가 같은 클래스를 선언하기만 하면 어떤 프로젝트의 외부에 있는 코드라도 패키지 내부에 있는 패키지 전용 선언에 쉽게 접근할 수 있다. 그래서 모듈의 캡슐화가 쉽게 깨진다.

다른차이는 코틀린에서는 최상위 선언에 대해 private 가시성을 허용한다는 점이다.  
최상위 선언에는 클래스, 함수, 프로퍼티 등이 포함된다. 비공개 가시성인 최상위 선언은 그 선언이 들어있는 파일 내부에서만 사용할 수 있다.  
이 또한 하위 시스템의 자세한 구현 사항을 외부에 감추고 싶을 때 유용한 방법이다.  
|변경자|클래스 멤버|최상위 선언|   
|:---:|:---:|:---:|  
|public(기본가시성)|모든 곳에서 볼 수 있다|모든 곳에서 볼 수 있다|  
|internal|같은 모듈 안에서만 볼 수 있다|같은 모듈 안에서만 볼 수 있다|  
|protected|하위 클래스 안에서만 볼 수 있다|최상위 선언에 적용할 수 없음|  
|private|같은 클래스 안에서만 볼 수 있다|같은 파일 안에서만 볼 수 있다|
```kotlin
internal open class TalkativeButton: Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

fun TalkativeButton.giveSpeech() { // 오류 : "public" 멤버가 자신의 "internal" 수신 타입인 "TalkativeButton"을 노출함
    yell() // 오류 : "yell"은 "TalkativeButton"의 private임.
    whisper() // 오류 : "whisper"는 "TalkativeButton"의 protected임.
}
```
어떤 클래스의 기반 타입 목록에 들어있는 타입이나 제네릭 클래스의 타입 파라미터에 들어있는 타입의 가시성은 그 클래스 자신의 가시성과 같거나 더 높아야하고,  메소드의 시그니처에 사용된 모든 타입의 가시성은 그 메소드의 가시성과 같거나 더 높아야 한다는 일반적인 규칙에 해당한다.

자바에서는 같은 패키지 안에서 protected 멤버에 접근할 수 있지만, 코틀린에서는 그렇지 않다는 점에 유의하라.  
코틀린의 가시성 규칙은 단순하다. protected 멤버는 오직 어떤 클래스나 그 클래스를 상속한 클래스 안에서만 보인다. 클래스를 확장한 함수는 그 클래스의 private이나 protected 멤버에 접근할 수 없다는 사실도 기억하자.

#### 내부 클래스와 중첩된 클래스
클래스 안에 다른 클래스를 선언할 수 있다.  
클래스 안에 다른 클래스를 선언하면 도우미 클래스를 캡슐화 하거나 코드 정의를 그 코드를 사용하는 곳 가까이에 두고 싶을 때 유용하다.

중첩 클래스(nested class)는 명시적으로 요청하지 않는 한 바깥쪽 클래스 인스턴스에 대한 접근 권한이 없다.  
클래스의 상태를 저장하는 클래스는 내부에 선언하면 편하다.
```kotlin
interface State: Serializable
interface View {
    fun getCurrentState(): State
    fun restoreStatez(state: State) {}
}
```  
```java
public class Button implements View {
    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override 
    public void restoreState(State state) { /*...*/ }

    public class ButtonState implements State { /*...*/ }
}
```
이 코드의 어디가 잘못되었을까? 왜 버튼의 상태를 직렬화하면 java.io.NotSerializableException:Button 이라는 오류가 발생할까?  

자바에서 다른 클래스 안에 정의한 클래스는 자동으로 내부 클래스(inner class)가 된다는 사실을 기억한다면 어디가 잘못된 건지 명확히 알 수 있다.  
ButtonState 클래스는 바깥쪽 Button 클래스에 대한 참조를 묵시적으로 포함한다. 그 참조로 인해 직렬화를 할 수 없다.

코틀린 코드를 보자.
```kotlin
class Button: View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { /*...*/ }
    class ButtonState: State { /*...*/ }
}
```
코틀린 중첩 클래스에 아무런 변경자가 붙지 않으면 자바 static 중첩 클래스와 같다.  
이를 내부 클래스로 변경해서 바깥쪽 클래스에 대한 참조를 포함하게 만들고 싶다면 inner 변경자를 붙여야 한다.

코틀린 바깥쪽 클래스의 인스턴스를 가리키는 참조를 표기하는 방법도 자바와 다르다.
```kotlin
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
```

중첩 클래스를 유용하게 사용하는 용례를 하나 살펴보자.  
클래스 계층을 만들되 그 계층에 속한 클래스의 수를 제한하고 싶은 경우 중첩 클래스를 쓰면 편리하다.

#### 봉인된 클래스: 클래스 계층 정의 시 계층 확장 제한
상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있다. sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.
```kotlin
sealed class Expr { // 기반 클래스를 sealed로 봉인한다.
    class Num(val value: Int): Expr() // 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다.
    class Sum(val left: Expr, val right: Expr): Expr()
}

fun eval(e: Expr): Int = 
    when (e) { // "when"식이 모든 하위 클래스를 검사하므로 별도의 "else" 분기가 없어도 된다.
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.left)+eval(e.right)
    }   
```
sealed로 표시된 클래스는 자동으로 open임을 기억하자.  
sealed 클래스에 속한 값에 대해 디폴트 분기를 사용하지 않고 when 식을 사용하면 나중에 sealed 클래스의 상속 계층에 새로운 하위 클래스를 추가해도 when 식이 컴파일되지 않는다. 따라서 when 식을 고쳐야 한다는 사실을 쉽게 알 수 있다.  
내부적으로 Expr 클래스는 private 생성자를 가진다. 그 생성자는 내부에서만 호출할 수 있다.  
sealed 인터페이스를 정의할 수는 없다. 왜 그럴까? 봉인된 인터페이스를 만들 수 있다면 그 인터페이스를 자바 쪽에서 구현하지 못하게 막을 수 있는 수단이 코틀린 컴파일러에게 없기 때문이다.

## 뻔하지 않은 생성자와 프로퍼티를 갖는 클래스 선언
코틀린은 주(primary) 생성자와 부(secondary) 생성자를 구분한다.  
주 생성자는 클래스를 초기화할 때 주로 사용하는 간략한 생성자로 클래스 본문 밖에서 정의한다.  
부 생성자는 클래스 본문 안에서 정의한다.  
코틀린에서는 초기화 블록(initializer block)을 통해 초기화 로직을 추가할 수 있다.  

#### 클래스 초기화: 주 생성자와 초기화 블록
```kotlin
class User(val nickname: String)
```
클래스 이름 뒤에 오는 괄호로 둘러싸인 코드를 주 생성자(primary constructor)라고 부른다.  
주 생성자는 생성자 파라미터를 지정하고 그 생성자 파라미터에 의해 초기화되는 프로퍼티를 정의하는 두 가지 목적으로 쓰인다.

가장 명시적인 선언으로 풀어보자.
```kotlin
class User constructor(_nickname: String) {
    val nickname: String
    init {
        nickname = _nickname
    }
}
``` 
constructor 키워드는 주 생성자나 부 생성자 정의를 시작할 때 사용한다.  
init 키워드는 초기화 블록을 시작한다. 초기화 블록에는 클래스의 객체가 만들어질 때 실행될 초기화 코드가 들어간다. 초기화 블록은 주 생성자와 함께 사용된다. 주 생성자는 제한적이기 때문에 초기화 블록이 필요하다.  

이 예에서는 nickname 프로퍼티를 초기화하는 코드를 nickname 프로퍼티 선언에 포함시킬 수 있어서 초기화 코드를 초기화 블록에 넣을 필요가 없다.  
또 주 생성자 앞에 별다른 애노테이션이나 가시성 변경자가 없다면 constructor를 생략해도 된다.
```kotlin
class User(_nickname: String) { // 파라미터가 하나뿐인 주 생성자
    val nickname = _nickname // 프로퍼티를 주 생성자의 파라미터로 초기화한다.
}
```
주 생성자의 파라미터로 초기화 한다면 주 생성자 파라미터 이름 앞에 val을 추가하는 방식으로 프로퍼티 정의와 초기화를 간략히 쓸 수 있다.
```kotlin
class User(val nickname: String,
    val isSubscribed: Boolean = true)
```
생성자 파라미터에도 디폴트 값도 정의할 수 있다. 클래스의 인스턴스를 만들려면 생성자를 호출하면 된다.
```kotlin
val user = User("solob")
println(user.isSubscribed)
``` 

클래스에 기반 클래스가 있다면 주 생성자에서 기반 클래스의 생성자를 호출해야 할 필요가 있다.
```kotlin
open class User(val nickname: String) { ... }
class TwitterUser(nickname: String): User(nickname) { ... }
```  
클래스를 정의할 때 별도로 생성자를 정의하지 않으면 컴파일러가 자동으로 아무 일도 하지 않는 인자가 없는 디폴트 생성자를 만들어준다.

예로 Button 생성자는 아무 인자도 받지 않지만, Button 클래스를 상속한 하위 클래스는 반드시 Button 클래스의 생성자를 호출해야한다.
```kotlin
class RadioButton: Button() 
```
이 규칙으로 인해 기반 클래스의 이름 뒤에는 꼭 빈 괄호가 들어간다. 물론 생성자 인자가 있다면 괄호 안에 인자가 들어간다.  
반 인터페이스는 생성자가 없기 때문에 인터페이스를 구현하는 경우 인터페이스 이름 뒤에는 아무 괄호도 없다.  

어떤 클래스를 외부에서 인스턴스화하지 못하게 막고 싶다면 모든 생성자를 private으로 만들면 된다.
```kotlin
class Secretive private constructor() { }
```

#### 비공개 생성자에 대한 대안
유틸리티 함수만 담아두는 역할만을 하는 클래스는 인스턴스화할 필요가 없고, 싱글턴인 클래스는 미리 정한 팩토리 메소드 등의 생성 방법을 통해서만 객체를 생성해야 한다. 자바에서는 이런 더 일반적인 요구 사항을 명시할 방법이 없으므로 어쩔 수 없이 private 생성자를 정의해서 클래스를 다른 곳에서 인스턴스화하지 못하게 막는 경우가 생긴다.

코틀린은 그런 경우를 언어에서 기본 지원한다.  
정적 유틸리티 함수 대신 최상위 함수를 사용할 수 있고, 싱글턴을 사용하고 싶으면 객체(object)를 선언하면 된다.

#### 부 생성자: 상위 클래스를 다른 방식으로 초기화
```kotlin
open class View {
    constructor(ctx: Context) {
        // ...
    }
    constructor(ctx: Context, attr: AttributeSet) {
        // ...
    }
}

class MyButton: View {
    constructor(ctx: Context): super(ctx) {
        // ...
    }
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr) {
        // ...
    }   
}
```
클래스에 주 생성자가 없다면 모든 부 생성자는 반드시 상위 클래스를 초기화하거나 다른 생성자에게 생성을 위임해야 한다.

부 생성자가 필요한 주된 이유는 자바 상호운용성이다. 하지만 부 생성자가 필요한 다른 경우도 있다. 클래스 인스턴스를 생성할 때 파라미터 목록이 다른 생성 방법이 여럿 존재하는 경우에는 부 생성자를 여럿 둘 수 밖에 없다.

#### 인터페이스에 선언된 프로퍼티 구현
코틀린에서는 인터페이스에 추상 프로퍼티를 선언에 넣을 수 있다.
```kotlin
interface User {
    val nickname: String
}
```
이는 User 인터페이스를 구현하는 클래스가 nickname의 값을 얻을 수 있는 방법을 제공해야 한다는 뜻이다.  
인터페이스에 있는 프로퍼티 선언에는 뒷받침하는 필드나 게터 등의 정보가 들어있지 않다. 인터페이스는 아무 상태도 포함할 수 없으므로 상태를 저장할 필요가 있다면 인터페이스를 구현한 하위 클래스에서 상태 저장을 위한 프로퍼티 등을 만들어야 한다.
```kotlin
class PrivateUser(override val nickname: String): User // 주 생성자에 있는 프로퍼티

class SubscribeUser(val email: String): User {  
    override val nickname: String 
        get() = email.substringBefore('@') // 커스텀 게터
}

class FacebookUser(val accountId: Int): User {
    override val nickname = getFacebookName(accountId) // 프로퍼티 초기화 식
}
```
인터페이스에는 추상 프로퍼티뿐 아니라 꼐터와 쎼터까 있는 프로퍼티를 선언할 수도 있다. 물론 그런 게터와 세터는 뒷받침하는 필드를 참조할 수 없다.
```kotlin
interface User {
    val email: String
    val nickname: String
        get() = email.substringBefore('@') // 프로퍼티에 뒷받침하는 필드가 없다. 대신 매번 결과를 계산해 돌려준다.
}
```
하위 클래스는 추상 프로퍼티인 email을 반드시 오버라이드해야 한다. 반면 nickname은 오버라이드하지 안혹 상속할 수 있다.  
인터페이스에 선언된 프로퍼티와 달리 클래스에 구현된 프로퍼티는 뒷받침하는 필드를 원하는 대로 사용할 수 있다.

#### 게터와 세터에서 뒷받침하는 필드에 접근
프로퍼티의 두 가지 유형. 값을 저장하는 프로퍼티와 커스텀 접근자에서 매번 값을 계산하는 유형을 살펴봤다.  
이제는 두 유형을 조합해서 어떤 값을 저장하되 그 값을 변경하거나 읽을 때마다 정해진 로직을 실행하는 유형의 프로퍼티를 만들어보자.  
값을 저장하는 동시에 로직을 실행할 수 있게 하기 위해서는 접근자 안에서 프로퍼티를 뒷받침하는 필드에 접근할 수 있어야 한다.
```kotlin
class User(val name: String) {
    val address: String = "unspecified"
        set(value: String) {
            println("""Address was changed for $name: "$field" -> "$value".""") // 뒷받침하는 필드 값 읽기
            field = value // 뒷받침하는 필드 값 변경하기
        }
}
```
코틀린에서 프로퍼티의 값을 바꿀 때는 user.address = "new value" 처럼 필드 설정 구문을 사용한다. 이 구문은 내부적으로 address의 세터를 호출한다.  

접근자의 본문에서는 field라는 특별한 식별자를 통해 뒷받침하는 필드에 접근할 수 있다.  
게터에서는 field 값을 읽을 수만 있고, 세터에서는 field값을 읽거나 쓸 수 있다.  
변경가능한 프로퍼티의 게터와 세터중 한 쪽만 직접 정의해도 된다.

컴파일러는 디폴트 접근자 구현을 사용하건 커스텀 게터나 세터를 정의하건 관계없이 field를 사용하는 프로퍼티에 대해 뒷받침하는 필드를 생성해준다. 다만 field를 사용하지 않는 커스텀 접근자 구현을 정의한다면 뒷받침하는 필드가 존재하지 않는다.  
val인 경우 게터에 field가 없으면 되지만, var인 경우 게터, 세터 모두에 field가 없어야 한다.

#### 접근자의 가시성 변경
접근자의 가시성은 기본적으로는 프로퍼티의 가시성과 같다. 하지만 원한다면 접근자 앞에 가시성을 붙여 변경할 수 있다.
```kotlin
class LengthCounter {
    var conter: Int = 0 
        private set // 클래스 밖에서 이 프로퍼티의 값을 바꿀 수 없다.
    fun addWord(word: String) {
        conter += word.length // 클래스 내부에서 값을 바꿀 수 있다.
    }
}
```
counter는 public으로 외부에 공개되지만 클래스 내부에서만 수정이 가능하다. (public get, private set)

## 컴파일러가 생성한 메소드: 데이터 클래스와 클래스 위임
자바 플랫폼에서는 클래스가 equals, hashCode, toString 등의 메소드를 구현해야 한다. 다행히 자바 IDE들이 이런 메소드를 자동으로 만들어준다. 하지만 자동으로 생성한다고해도 코드베이스가 번잡해진다는 면은 동일하다.

코틀린 컴파일러는 한걸음 더 나가서 이런 메소드를 기계적으로 생성하는 작업을 보이지 않는 곳에서 해준다. 따라서 필수 메소드로 인한 잡음 없이 소스코드를 깔끔하게 유지할 수 있다.

#### 모든 클래스가 정의해야 하는 메소드
자바와 마찬가지로 코틀린 클래스도 toString, equals, hashCode 등을 오버라이드할 수 있다.
```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) 
            return false
        return name == other.name && postalCode == other.postalCode
    }        
}
```
***문자열 표현: toString()***  
기본 제공되는 객체의 문자열 표현은 Client@5e9f23b4 같은 방식인데, 이는 그다지 유용하지 않다. 기본 구현을 바꾸려면 toString 메소드를 오버라이드해야 한다.

***객체의 동등성: equals()***
코틀린에서는 == 연산자가 두 객체를 비교하는 기본적인 방법이다.  
== 는 내부적으로 equals를 호출해서 객체를 비교한다. 따라서 클래스가 equals를 오버라이드하면 == 를 통해 안전하게 그 클래스의 인스턴스를 비교할 수 있다.  

참조 비교를 위해서는 === 연산자를 사용할 수 있다. === 연산자는 자바에서 참조를 비교할 때 사용하는 == 연산자와 같다.

오버라이드하고 나면 프로퍼티의 값이 모두 같은 두 Client 객체는 동등하리라 예상할 수 있다. 하지만 Client로 더 복잡한 작업을 수행해보면 제대로 작동하지 않는 경우가 있다. 이와 관련해서 흔히 면접에서 질문하는 내용이 "Client가 제대로 작동하지 않는 경우를 말하고 문제가 무엇인지 설명하시오"다.

hashCode 정의를 빠뜨려서 그렇다고 답하는 개발자가 많을 것이다. 이 경우 실제 hashCode가 없다는 점이 원인이다.

***해시 컨테이너: hashCode()***
자바에서는 equals를 오버라이드할 때 반드시 hashCode도 함께 오버라이드해야 한다.  
```kotlin
val processed = hashSetOf(Client("오현석", 4122))
println(processed.contains(Client("오현석", 4122))) // false
```
JVM 언어에서는 hashCode가 지켜야 하는 "equals()가 true를 반환하는 두 객체는 반드시 같은 hashCode()를 반환해야 한다"라는 제약이 있는데 Client는 이를 어기고 있다.

HashSet은 원소를 비교할 때 비용을 줄이기 위해 먼저 객체의 해시 코드를 비교하고 해시 코드가 같은 경우에만 실제 값을 비교한다. 해시 코드가 다를 때 equals가 반환하는 값은 판단 결과에 영향을 끼치지 못한다. 즉, 원소 객체들이 해시코드에 대한 규칙을 지키지 않는 경우 HashSet은 제대로 작동할 수 없다.

이 문제를 고치려면 Client는 hashCode를 구현해야 한다.
```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) 
            return false
        return name == other.name && postalCode == other.postalCode
    }    
    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
}
```
이제 이 클래스는 예상대로 작동한다. 하지만 지금까지 얼마나 많은 코드를 작성해야 했는지 생각해보라.  
다행히 코틀린 컴파일러는 이 모든 메소드를 자동으로 생성해줄 수 있다.

#### 데이터 클래스: 모든 클래스가 정의해야 하는 메소드 자동 생성
어떤 클래스가 데이터를 저장하는 역할만 수행한다면 toString, equals, hashCode를 반드시 오버라이드해야 한다.  
코틀린은 이런 메소드를 IDE를 통해 생성할 필요도 없이 data라는 변경자를 클래스 앞에 붙이면 필요한 메소드를 컴파일러가 자동으로 만들어준다.  
data 변경자가 붙은 클래스를 데이터 클래스라고 부른다.
```kotlin
data class Client(val name: String, val postalCode: Int)
```
이제 Client 클래스는 자바에서 요구하는 모든 메소드를 포함한다.
- 인스턴스 간 비교를 위한 equals
- HashMap과 같은 해시 기반 컨테이너에서 키로 사용할 수 있는 hashCode
- 클래스의 각 필드를 선언 순서대로 표시하는 문자열 표현을 만들어주는 toString

equals와 hashCode는 주 생성자에 나열된 모든 프로퍼티를 고려해 만들어진다.  
주 생성자 밖에 정의된 프로퍼티는 equals나 hashCode를 계산할 때 고려의 대상이 아니라는 사실에 유의하라.

***데이터 클래스와 불변성: copy() 메소드***
데이터 클래스의 프로퍼티가 꼭 val일 필요는 없다. 하지만 데이터 클래스의 모든 프로퍼티를 읽기 전용으로 만들어서 데이터 클래스를 불변(immutable) 클래스로 만들라고 권장한다.

HashMap 등의 컨테이너에 데이터 클래스 객체를 담는 경우엔 불변성이 필수적이다.  
데이터 클래스 객체를 키로 하는 값을 컨테이너에 담은 키로 쓰인 데이터 객체의 프로퍼티를 변경하면 컨테이너 상태가 잘못될 수도 있다.

불변 객체를 주로 사용하는 프로그램에서는 스레드가 사용중인 데이터를 다른 스레드가 변경할 수 없으므로 스레드를 동기화해야 할 필요가 줄어든다.

데이터 클래스 인스턴스를 불변 객체로 더 쉽게 활용할 수 있게 코틀린 컴파일러는 한 가지 편의 메소드를 제공한다.  
그 메소드는 객체를 복사(copy)하면서 일부 프로퍼티를 바꿀 수 있게 해주는 copy 메소드다.  
객체를 메모리 상에서 직접 바꾸는 대신 복사본을 만드는 편이 더 낫다. 복사본은 제거해도 프로그램에서 원본을 참조하는 다른 부분에 전혀 영향을 끼치지 않는다.
```kotlin
val lee = Client("이계영", 4122)
println(lee.copy(postalCode=4000)) // Client(name="이계영", postalCode=4000)
println(lee) // Client(name="이계영", postalCode=4122)
```

#### 클래스 위임: by 키워드 사용
상위 클래스의 구현이 바뀌거나 상위 클래스에 새로운 메소드가 추가되는 과정에서 하위 클래스가 상위 클래스에 대해 갖고 있던 가정이 깨져서 코드가 정상적으로 작동하지 못하는 경우가 생길 수 있다.

코틀린을 설계하면서 이런 문제를 인식하고 기본적으로 클래스를 final로 취급하기로 결정했다. 열린 상위 클래스의 소스코드를 변경할 때는 oepn 변경자를 보고 해당 클래스를 다른 클래스가 상속하리라 예상할 수 있으므로, 변경 시 하위 클래스를 깨지 않기 위해 좀 더 조심할 수 있다.

하지만 종종 상속을 허용하지 않는 클래스에 새로운 동작을 추가해야 할 때가 있는데 일반적인 방법이 데코레이터 패턴이다.  
이 패턴의 핵심은 상속을 허용하지 않는 클래스(기존 클래스)대신 사용할 수 있는 새로운 클래스(데코레이터)를 만들되 기존 클래스와 같은 인터페이스를 데코레이터가 제공하게 만들고, 기존 클래스를 데코레이터 내부에 필드로 유지하는 것이다. 이때 새로 정의해야하는 기능은 데코레이터의 메소드에 정의하고 기존 기능이 그대로 필요한 부분은 ㄷ데코레이터의 메소드가 기존 클래스의 메소드에게 요청을 전달(forwarding)한다.

```kotlin
class DelegatingCollection<T>: Collection<T> {
    private val innerList = arrayListOf<T> ()
    
    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll (elements: Collection<T>): Boolean = innerList.containsAll(elements)
}
```
이런 접근 방법의 단점은 준비 코드가 상당히 많이 필요하다는 점이다.  
위임을 언어가 제공하는 일급 시민 기능으로 지원한다는 점이 코틀린의 장점이다.  
인터페이스를 구현할 때 by 키워드를 통해 그 인터페이스에 대한 구현을 다른 객체에 위임 중이라는 사실을 명시할 수 있다.
```kotlin
class DelegatingCollection<T> (innerList: Collection<T> = ArrayList<T>() ): Collection<T> by innerList { }
```
클래스 안에 있던 모든 메소드 정의가 없어졌다. 컴파일러가 그런 전달 메소드를 자동으로 생성하며 자동 생성한 코드의 구현은 DelegatingCollection에 있던 구현과 비슷하다.

메소드 중 일부의 동작을 변경하고 싶은 경우 메소드를 오버라이드하면 컴파일러가 생성한 메소드 대신 오버라이드한 메소드가 쓰인다.
```kotlin
class CountingSet<T> (
    val innerSet: MutableCollection<T> = HashSet<T> ()
): MutableCollection<T> by innerSet { // MutableCollection의 구현을 innerSet에 위임한다
    var objectsAdded = 0
    
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }
    
    override fun addAll(elements: Collection<T>): Boolean{
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}
```
코틀린 컴파일러가 클래스에 유용한 메소드를 생성해주는 방식에 대한 설명을 마친다.

## object 키워드: 클래스 선언과 인스턴스 생성
코틀린에서는 object 키워드를 다양한 상황에서 사용하지만 모든 경우 클래스를 정의하면서 동시에 인스턴스(객체)를 생성한다는 공통점이 있다.

object 키워드를 사용하는 여러 상황을 살펴보자
- 객체 선언은 싱글턴을 정의하는 방법 중 하나다.
- 동반 객체는 인스턴스 메소드는 아니지만 클래스와 관련 있는 메소드와 팩토리 메소드를 담을 때 쓰인다.  
동반 객체 메소드에 접근할 때는 동반 객체가 포함된 클래스의 이름을 사용할 수 있다.
- 객체 식은 자바의 무명 내부 클래스 대신 쓰인다.

#### 객체 선언: 싱글턴을 쉽게 만들기
코틀린은 객체 선언 기능을 통해 싱글턴을 언어에서 기본 지원한다.  
객체 선언은 클래스 선언과 그 클래스에 속한 단일 인스턴스의 선언을 합친 선언이다.

객체 선언은 object 키워드로 시작한다. 객체 선언은 클래스를 정의하고 그 클래스의 인스턴스를 만들어서 변수에 저장하는 모든 작업을 단 한문장으로 처리한다.

클래스와 마찬가지로 객체 선언 안에도 프로퍼티, 메소드 초기화 블록 등이 들어갈 수 있다. 하지만 생성자는 객체 선언에 쓸 수 없다. 일반 클래스 인스턴스와 달리 싱글턴 객체는 객체 선언문이 있는 위치에서 생성자 호출 없이 즉시 만들어진다.

변수와 마찬가지로 객체 선언에 사용한 이름 뒤에 마침표(.)를 붙이면 객체에 속한 메소드나 프로퍼티에 접근할 수 있다.

객체 선언도 클래스나 인터페이스를 상속할 수 있다.
```kotlin
object CaseInsensitiveFileComparator: Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user"))) // 0
```
일반 객체를 사용할 수 있는 곳에서는 항상 싱글턴 객체를 사용할 수 있다.  
코틀린 객체 선언은 유일한 인스턴스에 대한 정적인 필드가 있는 자바 클래스로 컴파일 된다. 이때 인스턴스 필드의 이름은 항상 인스턴스다.  
CaseInsensetiveFileComparator.INSTANCE.compare(file1, file2);  

의존 관계가 별로 많지 않은 소규모 소프트웨어에서는 싱글턴이나 객체 선언이 유용하지만, 시스템을 구현하는 다양한 구성 요소와 상호작용하는 대규모 컴포넌트에는 싱글턴이 적합하지 않다.  
이유는 객체 생성을 제어할 방법이 없고 생성자 파라미터를 지정할 수 없어서다.

생성을 제어할 수 없고 생성자 파라미터를 지정할 수 없으므로 단위 테스트를 하거나 소프트웨어 시스템의 설정이 달라질 때 객체를 대체하거나 객체의 의존관계를 바꿀 수 없다. 따라서 그런 기능이 필요하다면 자바와 마찬가지로 의존관계 주입 프레임워크와 코틀린 클래스를 함께 사용해야 한다.

클래스 안에서 객체를 선언할 수도 있다. 그런 객체도 인스턴스는 단 하나 뿐이다.  
예를 들어 어떤 클래스의 인스턴스를 비교하는 Comparator를 클래스 내부에 정의하는게 더 바람직하다.
```kotlin
data class Person(val name: String) {
    object NameComparator: Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int = p1.name.compareTo(p2.name)
    }
}
```

#### 동반 객체: 팩토리 메소드와 정적 멤버가 들어갈 장소
코틀린 클래스에는 정적인 멤버가 없다. 코틀린 언어는 자바 static 키워드를 지원하지 않는다.  
그 대신 코틀린에서는 패키지 수준의 최상위 함수(자바의 정적 메소드 역할을 거의 다 할 수 있다)와 객체 선언(자바의 정적 메소드 역할 중 코틀린 최상위 함수가 대신할 수 없는 역할이나 정적 필드를 대신할 수 있다)을 활용한다.  
대부분의 경우 최상위 함수를 활용하는 편을 더 권장한다. 
하지만 최상위 함수는 private으로 표시된 클래스 비공개 멤버에 접근할 수 없다. 
그래서 클래스의 인스턴스와 관계없이 호출해야 하지만 클래스 내부 정보에 접근해야 하는 함수가 필요할 때는 클래스에 중첩된 객체 선언의 멤버 함수로 정의해야 한다. 
대표적인 예로 팩토리 메소드를 들 수 있다.

클래스 안에 정의된 객체 중 하나에 companion이라는 특별한 표시를 붙이면 그 클래스의 동반 객체로 만들 수 있다.  
이때 객체의 이름을 따로 지정할 필요가 없다. 그 결과 동반 객체의 멤버를 사용하는 구문은 자바의 정적 메소드 호출이나 정적 필드 사용 구문과 같아진다.
```kotlin
class A {
    companion object {
        fun bar() {
            println("COmpanion object called")
        }
    }
}

A.bar()
```
동반 객체가 private 생성자를 호출하기 좋은 위치다. 
동반 객체는 자신을 둘러싼 클래스의 모든 private 멤버에 접근할 수 있다. 
따라서 동반 객체는 바깥쪽 클래스의 private 생성자도 호출할 수 있다. 따라서 동반 객체는 팩토리 패턴을 구현하기 가장 적합한 위치다.
```kotlin
class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) = User(email.substring('@'))
        fun newFacebookUser(accountId: Int) = User(getFacebookName(accountId))
    }
}

val subscribingUser = User.newSubscribingUser("bob@email.com")
val facebookUser = User.newFacebookUser(4)

println(subscribingUser.nickname)
```
팩토리 메소드는 매우 유용하다. 팩토리 메소드는 목적에 따라 메소드 이름을 정할 수 있고, 팩토리 메소드가 선언된 클래스의 하위 클래스 객체를 반환할 수도 있다. 
또 팩토리 메소드는 생성할 필요가 없는 객체를 생성하지 않을 수도 있다.

예를 들어 이메일 주소별로 유일한 User 인스턴스를 만드는 경우 팩토리 메소드가 이미 존재하는 인스턴스에 해당하는 이메일 주소를 전달받으면 새 인스턴스를 만들지 않고 캐시에 있는 기존 인스턴스를 반환할 수 있다.  
하지만 클래스를 확장해야만 하는 경우에는 동반 객체 멤버를 하위 클래스에서 오버라이드할 수 없으므로 여러 생성자를 사용하는 편이 더 나은 해법이다.

#### 동반 객체를 일반 객체처럼 사용
동반 객체는 클래스 안에 정의된 일반 객체다. 따라서 동반 객체에 이름을 붙이거나, 인터페이스를 상속하거나, 확장 함수와 프로퍼티를 정의할 수 있다.

동반 객체의 이름을 지을 수 있지만 이름을 짓지 않는다면 자동으로 Companion이 된다.  
```kotlin
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T 
}

class Person(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = ...
    }
}

fun loadFromJSON<T>(factory: JSONFactory<T>): T {
    ...
}

loadFromJSON(Person)
```
여기서 동반 객체가 구현한 JSONFactory의 인스턴스를 넘길 때 Person 클래스의 이름을 사용했다는 점에 유의하라

동반 객체에 대한 확장 함수도 만들 수 있다.
```kotlin
class Person(val firstName: String, val lastName: String) {
    companion object {
    }
}

fun Person.Companion.fromJSON(json:String): Person { ... }
```
동반 객체 안에서 fromJSON 함수를 정의한 것처럼 fromJSON을 호출할 수 있다.  
하지만 실제로 fromJSON은 클래스 밖에서 정의한 확장 함수다. 
여기서 동반 객체에 확장 함수를 작성할 수 있으려면 원래 클래스에 동반 객체를 꼭 선언해야 한다는 점에 주의하라. 설령 빈 동반 객체라도 꼭 있어야 한다.

#### 객체 식: 무명 내부 클래스를 다른 방식으로 작성
무명 객체를 정의할 때도 object 키워드를 쓴다. 무명 객체는 자바의 무명 내부 클래스를 대신한다.
```kotlin
window.addMouseListener(
    object: MouseAdapter() {
        override fun mouseClicked(e: MouseEvent()) { ... }
        override fun mouseEntered(e: MouseEvent()) { ... }
    }
)
```
객체 식은 클래스를 정의하고 그 클래스에 속한 인스턴스를 생성하지만, 그 클래스나 인스턴스에 이름을 붙이지 않는다.  
이런 경우 보통 함수를 호출하면서 인자로 무명 객체를 넘기기 때문에 클래스와 인스턴스 모두 이름이 필요하지 않다.  
하지만 객체에 이름을 붙여야 한다면 변수에 무명 객체를 대입하면 된다.  

객체 선언과 달리 무명 객체는 싱글턴이 아니다. 객체 식이 쓰일 때마다 새로운 인스턴스가 생성된다.

자바의 무명 클래스와 같이 객체 식 안의 코드는 그 식이 포함된 함수의 변수에 접근할 수 있다. 하지만 자바와 달리 final이 아닌 변수도 객체 식 안에서 사용할 수 있다.
```kotlin
fun countClicks(window: Window) {
    var clickCount = 0
    
    window.addMouseListener(object: MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }
    })
}
```
객체 식은 무명 객체 안에서 여러 메소드를 오버라이드해야 하는 경우에 훨씬 더 유용하다.