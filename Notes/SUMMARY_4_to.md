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