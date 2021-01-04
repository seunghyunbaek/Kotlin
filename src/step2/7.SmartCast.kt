package step2

import java.lang.IllegalArgumentException

// (1+2)+4와 같은 간단한 산술식을 계산하는 함수를 만들 것이다.
// 함수가 받을 산술식에서는 오직 두 수를 더하는 연산만 가능하다. 다른 연산(뺄셈, 곱셈, 나눗셈)도 비슷한 방식으로 구현할 수 있다.

// 우선 식을 인코딩하는 방법을 생각해야 한다. 식을 트리 구조로 저장하자.
// 노드는 합계(Sum)나 수(Num) 중 하나다.
// Num은 항상 말단(leaf 또는 terminal)노드지만, Sum은 자식이 둘 있는 중간(non-terminal)노드다.
// Sum노드의 두 자식은 덧셈의 두 인자다.

interface Expr // 식을 위한 인터페이스
// Expr은 아무 메소드도 선언하지 않으며, 단지 여러 타입의 식 객체를 아우르는 공통 타입 역할만 수행한다.
// 클래스가 구현하는 인터페이스를 지정하기 위해 콜론(:) 뒤에 인터페이스 이름을 사용한다.
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr // Expr 타입의 객체라면 어떤 것이나 Sum연산의 인자가 될 수 있다. 따라서 Num이나 다른 Sum이 인자로 올 수 있다.

// (1+2)+4라는 식을 저장하면 Sum(Sum(Num(1), Num(2)), Num(4)) 라는 구조의 객체가 생긴다.
// Expr 인터페이스에는 두 가지 구현 클래스가 존재한다. 따라서 식을 평가하려면 두 가지 경우를 고려해야 한다.
    // 어떤 식이 수라면 그 값을 반환한다.
    // 어떤 식이 합계라면 좌항과 우항의 값을 계산한 다음에 그 두 값을 합한 값을 반환한다.

// 먼저 자바 스타일의 함수이다. 자바였다면 조건을 검사하기 위해 if문을 사용했을 것이다.
fun evalJava(e: Expr): Int {
    if (e is Num) {
        val n = e as Num // 여기서 Num으로 타입을 변환하는데, 이는 불필요한 중복이다.
        return n.value
    }
    if (e is Sum) {
        return evalJava (e.right) + evalJava(e.left) // 변수 e에 대해 스마트 캐스트를 사용한다.
    }
    throw IllegalArgumentException("Unknown expression")
}

// 코틀린에서는 is를 사용해 변수 타입을 검사한다. C#을 아는 개발자라면 is가 낯익을 것이다.
// is 검사는 자바의 instanceof와 비슷하다. 하지만 자바에서 어떤 변수의 타입을 instanceof로 확인한 다음에 그 타입에 속한 멤버에 접근하기 위해서는 명시적으로 변수 타입으로 캐스팅해야 한다.
// 코틀린에서는 프로그래머 대신 컴파일러가 캐스팅을 해준다.
// 어떤 변수가 원하는 타입인지 일단 is로 검사하고 나면 굳이 변수를 원하는 타입으로 캐스팅하지 않아도 마치 처음부터 그 변수가 원하는 타입으로 선언된 것처럼 사용할 수 있다.
// 하지만 실제로는 컴파일러가 캐스팅을 수행해준다. 이를 스마트 캐스트(smart cast)라고 부른다.
// ★ 스마트 캐스트는 is로 변수에 든 값의 타입을 검사한 다음에 그 값이 바뀔 수 없는 경우에만 작동한다.
// 클래스의 프로퍼티에 대해 스마트 캐스트를 사용한다면 반드시 val이어야 하며 커스텀 접근자를 사용한 것이면 안된다.
// 원하는 타입으로 명시적으로 캐스팅하려면 as 키워드를 사용한다.

// 이제 코틀린다운 코드로 만들어보자
fun evalKotlinIf(e: Expr): Int =
        if (e is Num) {
            e.value
        } else if (e is Sum) {
            evalKotlinIf(e.left) + evalKotlinIf(e.right)
        } else {
            throw IllegalArgumentException("Unknown expression")
        }

// 위 코드를 when으로 변경해보자
fun evalKotlinWhen(e: Expr): Int =
        when (e) {
            is Num -> // 인자 타입을 검사하는 when 분기들
                e.value // 스마트 캐스트
            is Sum ->
                evalKotlinWhen(e.left) + evalKotlinWhen(e.right) // 스마트 캐스트
            else ->
                throw IllegalArgumentException("Unknown expression")
        }

// 블록의 마지막 식이 블록의 결과라는 규칙은 블록이 값을 만들어내야 하는 경우 항상 성립한다.
// 하지만 이 규칙은 함수에 대해서는 성립하지 않는다.
// 식이 본문인 함수는 블록을 본문으로 가질 수 없고 블록이 본문인 함수는 내부에 return문이 반드시 있어야 한다.
fun evalWithLogging(e: Expr): Int =
        when(e) {
            is Num -> {
                println("num: ${e.value}")
                e.value // 분기의 마지막 문장이 전체의 결과가 된다.
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("sum: $left + $right")
                left + right
            }
            else -> throw IllegalArgumentException("Unknown expreesion")
        }

fun main(args: Array<String>) {
    println(evalJava(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalKotlinIf(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))
}