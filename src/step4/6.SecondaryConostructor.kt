package step4

import javax.naming.Context
import javax.print.attribute.Attribute
import javax.print.attribute.AttributeSet

// 부 생성자: 상위 클래스를 다른 방식으로 초기화

// Tip. 인자에 대한 디폴트 값을 제공하기 위해 부 생성자를 여럿 만들지 말라. 대신 파라미터의 디폴트 값을 생성자 시그니처에 직접 명시하라.
// 코틀린의 디폴트 파라미터 값과 이름붙은 인자 문법을 사용해 오버로드한 생성자가 필요한 상황 중 상당수는 해결할 수 있다.
// 그래도 생성자가 여럿 필요한 경우가 가끔 있다. 가장 일반적인 상황은 프레임워크 클래스를 확장해야 하는데 여러 가지 방법으로 인스턴스를 초기화할 수 있게 다양한 생성자를 지원해야 하는 경우다.
// 예를 들어 자바에서 선언된 생성자가 2개인 View 클래스가 있다고 하자(안드로이드 개발자라면 이 클래스를 알아볼 수 있을 것이다). 그 클래스를 코틀린으로는 다음과 비슷하게 정의할 수 있다.
open class View2 {
    constructor(ctx: Context) {} // 부 생성자
    constructor(ctx: Context, attr: AttributeSet) {} // 부생성자
}
// 이 클래스는 주 생성자를 선언하지 않고(클래스 헤더에 있는 클래스 이름 뒤에 괄호가 없다), 부 생성자만 2가지 선언한다. 부 생성자는 constructor 키워드로 시작한다.

// 이 클래스를 확장하면서 똑같이 부 생성자를 정의할 수 있다.
/*
class MyButotn: View2 {
    constructor(ctx: Context): super(ctx) {} // 상위 클래스의 생성자를 호출한다. 생성을 위임한다.
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr) {} // 상위 클래스의 생성자를 호출한다. 생성을 위임한다.
}
 */
// 여기서 두 부 생성자는 super() 키워드를 통해 자신에 대응하는 상위 클래스 생성자를 호한다.
// 자바와 마찬가지로 생성자에서 this()를 통해 클래스 자신의 다른 생성자를 호출할 수 있다.
/*
class MyButton: View2 {
    constructor(ctx: Context): this(ctx, MY_STYLE) {} // 이 클래스의다른 생성자에게 위임한다.
    constructor(ctx: Context, attr: AttributeSet) {}
}
*/

// 클래스에 주 생성자가 없다면 모든 부 생성자는 반드시 상위 클래스를 초기화하거나 다른 생성자에게 위임해야 한다.
// 부 생성자가 필요한 주된 이유는 자바 상호운용성이다.
// 하지만 부 생성자가 필요한 다른 경우도 있다. 클래스 인스턴스를 생성할 때 파라미터 목록이 다른 생성 방법이 여럿 존재하는 경우에는 부 생성자를 여럿 둘 수 밖에 없다.
// 지금까지는 뻔하지 않은 생성자를 정의하는 방법을 살펴봤다. 이제는 뻔하지 않은 프로퍼티를 살펴보자.