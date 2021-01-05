package step4

import java.io.Serializable

// 코틀린과 자바 가시성 규칙의 또 다른 차이는 코틀린에서는 외부 클래스가 내부 클래스나 중첩된 클래스의 private 멤버에 접근할 수 없다는 점이다.
// 내부 클래스와 중첩된 클래스 : 기본적으로 중첩 클래스

// 자바처럼 코틀린에서도 클래스 안에 다른 클래스를 선언할 수 있다.
// 클래스 안에 다른 클래스를 선언하면 도우미 클래스를 캡슐화하거나 코드 정의를 그 코드를 사용하는 곳 가까이에 두고 싶을 때 유용하다.
// 자바와의 차이는 코틀린의 중첩 클래스(nested class)는 명시적으로 요청하지 않는 한 바깥쪽 클래스 인스턴스에 대한 접근 권한이 없다는 점이다.
// 예제를 통해 이런 특성이 왜 중요한지 알아보자.

// View 요소를 하나 만든다고 상상해보자. 그 View의 상태를 직렬화해야 한다. 뷰를 직렬화하는 일은 쉽지 않지만 필요한 모든 데이터를 다른 도우미 클래스로 복사할 수는 있다.
// 이를 위해 State 인터페이스를 선언하고 Serializable을 구현한다. View 인터페이스 안에는 뷰의 상태를 가져와 저장할 때 사용할 getCurrentState와 restoreState 메소드 선언이 있다.

/* 리스트 4.9 직렬화할 수 있는 상태가 있는 뷰 선언 */
interface State: Serializable
interface View {
    fun getCurrentState() : State
    fun restoreState(state: State) {}
}

// Button 클래스의 상태를 저장하는 클래스는 Button 클래스 내부에 선언하면 편하다.
// 자바에서 그런 선언을 어떻게 하는지 살펴보자(이와 비슷한 코틀린 코드를 잠시 후 소개한다).
/* 자바에서 내부 클래스를 사용해 View 구현하기
public class Button implements View {
    @Override
    public State getCurrentState() { return new ButtonState(); }

    @Override
    public void restoreState(State state) { /*...*/ }

    public class ButtonState implements State { /* ... */ }
*/

// State 인터페이스를 구현한 ButtonState 클래스를 정의해서 Button에 대한 구체적인 정보를 저장한다. getCurrentState 메소드 안에서는 ButtonState의 새 인스턴스를 만든다. 실제로는 ButtonState 안에 필요한 모든 정보를 추가해야 한다.
// 이 코드의 어디가 잘못된 걸까? 왜 선언한 버튼의 상태를 직렬화하면 java.io.NotSerializableException:Button 이라는 오류가 발생할까? 처음에는 이 상황이 이상해 보일지도 모르겠다.
// 직렬화하려는 변수는 ButtonState 타입의 state였는데 왜 Button을 직렬화할 수 없다는 예외가 발생할까?
// 자바에서 다른 클래스 안에 정의한 클래스는 자동으로 내부 클래스(inner class)가 된다는 사실을 기억한다면 어디가 잘못된 건지 명확히 알 수 있다.
// 이 예제의 ButtonState 클래스는 바깥쪽 Button 클래스에 대한 참조를 묵시적으로 포함한다. 그 참조로 인해 ButtonState를 직렬화 할 수 없다. Button을 직렬화할 수 없으므로 버튼에 대한 참조가 ButtonState의 직렬화를 방해한다.
// 이 문제를 해결하려면 ButtonSatate를 static 클래스로 선언해야 한다. 자바에서 중첩 클래스를 static으로 선언하면 그 클래스를 둘러싼 바깥쪽 클래스에 대한 묵시적인 참조가 사라진다.

// 코틀린에서 중첩된 클래스가 기본적으로 동작하는 방식은 방금 설명한 것과 정반대다. 다음 예제를 보자.
/* 리스트 4.11 중첩 클래스를 사용해 코틀린에서 View 구현하기 */
class Button2 : View { // step4에 1.Class.kt 에 Button 클래스가 있어 Button2로 만듦.
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { super.restoreState(state) }
    class ButtonState : State {} // 이 클래스는 자바의 정적 중첩 클래스와 대응한다.
}

// 코틀린 중첩 클래스에 아무런 변경자가 붙지 않으면 자바 static 중첩 클래스와 같다. 이를 내부 클래스로 변경해서 바깥쪽 클래스에 대한 참조를 포함하게 만들고 싶다면 inner 변경자를 붙여야 한다.
// 다음은 이와 관련한 자바와 코틀린 사이의 차이를 보여준다. 중첩 클래스와 내부 클래스 사이의 차이를 볼 수 있다.

// 중첩 클래스 (바깥쪽 클래스에 대한 참조를 저장하지 않음)
// 자바 : static class A
// 코틀린 : class A
/*
class Outer {
    class Nested {}
}
 */

// 내부 클래스 (바깥쪽 클래스에 대한 참조를 저장함)
// 자바 : class A
// 코틀린 : inner class A
/*
class Outer {
    inner class Inner {
        this@Outer
    }
}
 */

// 코틀린에서 바깥쪽 클래스의 인스턴스를 가리키는 참조를 표기하는 방법도 자바와 다르다. 내부 클래스 Inner 안에서 바깥쪽 클래스 Outer의 참조를 접근하려면 this@Outer 라고 써야 한다.
class Outer {
    inner class Inner {
        fun getCurrentReference() : Outer = this@Outer
    }
}

// 자바와 코틀린의 내부 클래스와 중첩 클래스 간의 차이에 대해 배웠다.
// 다음에는 코틀린 중첩클래스를 유용하게 사용하는 용례를 하나 살펴보자.
// 클래스 계층을 만들되 그 계층에 속한 클래스의 수를 제한하고 싶은 경우 중첩클래스를 쓰면 편리하다.