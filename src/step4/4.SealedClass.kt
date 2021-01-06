package step4

// 봉인된 클래스 : 클래스 계층 정의 시 계층 확장 제한

// step2 에서 살펴본 식을 표현하는 클래스 계층을 다시 생각해보자. (step2 7.SmartCast.kt)
// 상위 클래스인 Expr 에는 숫자를 표현하는 Num과 덧셈 연산을 표현하는 Sum이라는 두 하위 클래스가 있다.
// when 식에서 이 모든 하위 클래스를 처리하면 편리하다. 하지만 when 식에서 Num과 Sum이 아닌 경우를 처리하는 else 분기를 반드시 넣어줘야만 한다.

// 코틀린 컴파일러는 when을 사용해 Expr 타입의 값을 검사할 때 꼭 디폴트 분기인 else 분기를 덧붙이게 강제한다. 이 예제의 else 분기에서는 반환할 만한 의미 있는 값이 없으므로 예외를 던진다.
// 항상 디폴트 분기를 추가하는게 편하지는 않다. 그리고 디폴트 분기가 있으면 이런 클래스 계층에 새로운 하위 클래스를 추가하더라도 컴파일러가 when이 모든 경우를 처리하는지 제대로 검사할 수 없다.
// 혹 실수로 새로운 클래스 처리를 잊어버렸더라도 디폴트 분기가 선택되기 때문에 심각한 버그가 발생할 수 있다.
// 코틀린은 이런 문제의 해법을 제공한다. sealed 클래스가 그 답이다. 상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있다.
// sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.
sealed class Expr { // 기반 클래스를 sealed로 봉인한다.
    class Num(val value: Int): Expr() {} // 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다.
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(e: Expr): Int =
        when (e) {
            is Expr.Num -> e.value
            is Expr.Sum -> eval(e.left) + eval(e.right)
            is SubExpr.Minus -> eval(e.left) - eval(e.right)
        }

// when 식에서 sealed 클래스의 모든 하위 클래스를 처리한다면 디폴트 분기(else 분기)가 필요 없다.
// sealed로 표시된 클래스는 자동으로 open임을 기억하라. 따라서 별도로 open  변경자를 붙일 필요가 없다.
// 봉인된 클래스는 외부에 자신을 상속한 클래스를 둘 수 없다.
// sealed 클래스에 속한 값에 대해 디폴트 분기를 사용하지 않고 when 식을 사용하면 나중에 sealed 클래스의 상속 계층에 새로운 하위 클래스를 추가해도 when 식이 컴파일되지 않는다. 따라서 when 식을 고쳐야 한다는 사실을 쉽게 알 수 있다.
// 내부적으로 Expr 클래스는 private 생성자를 가진다. 그 생성자는 클래스 내부에서만 호출할 수 있다.

// sealed 인터페이스를 정의할 수는 없다. 왜 그럴까? 봉인된 인터페이스를 만들 수 있다면 그 인터페이스를 자바 쪽에서 구현하지 못하게 막을 수 있는 수단이 코틀린 컴파일러에게 없기 때문이다.

// 코틀린 1.0에서 sealed는 너무 제약이 심하다. 예를 들어 모든 하위 클래스는 중첩 클래스여야 하고, 데이터 클래스로 sealed 클래스를 상속할 수도 없다.
// 코틀린 1.1부터는 이 제한이 완화됐다. 봉인된 클래스와 같은 파일의 아무데서나 봉인된 클래스를 상속한 하위 클래스를 만들 수 있고, 데이터 클래스로 하위 클래스를 정의할 수도 있다.
sealed class SubExpr: Expr() { // 이런 느낌의 코드일까?
    class Minus(val left: Expr, val right: Expr) : SubExpr()
}

// 기억하겠지만 코틀린에서는 클래스를 확장할 때나 인터페이스를 구현할 때 모두 콜론(:)을 사용한다. 하위 클래스 선언을 자세히 살펴보자.
/* class Num(val value: Int): Expr() */
// 여러분은 이 선언에서 맨 마지막의 Expr()에 쓰인 괄호를 제외한 모든 부분을 명확히 이해할 수 있어야 한다. --> Expr클래스를 상속하고 있으며 Int타입의 value라는 프로퍼티를 가진 Num 클래스를 정의한다.(이 정도의 해석이 맞을까?)
// Expr()에 쓰인 괄호에 대해서는 코틀린의 클래스 초기화에 대해 다루는 다음에서 설명한다.

fun main() {
    val n: Expr.Num = Expr.Num(1)
    val m: Expr.Num = Expr.Num(2)

    println(eval(Expr.Sum(n, m)))
    println(eval(SubExpr.Minus(n, m)))
}