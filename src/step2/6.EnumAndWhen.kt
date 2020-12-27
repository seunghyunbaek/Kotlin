package step2
import step2.Color.*
// enum은 자바 선언보다 코틀린 선언에 더 많은 키워드를 써야 하는 흔치 않은 예다.
// 코틀린에서는 enum class이지만 자바에서는 enum을 쓴다.
// 코틀린에서 enum은 소프트 키워드(soft keyword)라 부르는 존재다.
// enum은 class 앞에 있을 때만 특별한 의미를 지니지만 다른 곳에서는 이름에 사용할 수 있다.
// 반면 class는 키워드이다. 따라서 클래스를 표현하는 변수 등을 정의할 때는 clazz나 aClass와 같은 이름을 사용해야 한다.
// 자바와 마찬가지로 enum은 단순히 값만 열거하는 존재가 아니다.
// enum 클래스 안에도 프로퍼티나 메소드를 정의할 수 있다.

//enum class Color {
//    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
//}

// enum에서도 일반적인 클래스와 마찬가지로 생성자와 프로퍼티를 선언한다.
enum class Color(
        val r: Int, val g: Int, val b: Int // 상수의 프로퍼티를 정의
) {
    RED(255, 0, 0), ORANGE(255, 165, 0), // 각 상수를 생성할 때 그에 대한 프로퍼티 값을 지정해야 한다.
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 9, 130), VIOLET(238, 130, 238); // enum 클래스안에 메소드를 정의하는 경우 반드시 상수목록과 메소드 정의사이에 세미콜론을 사용해야 한다.

    fun rgb() = (r * 256 + g) * 256 + b // enum 클래스 안에서 메소드를 정의한다.
}

// when은 자바의 switch를 대치하되 훨씬 더 강력하며, 자주 사용할 프로그래밍 요소이다.
// if와 마찬가지로 when도 값을 만들어내는 식(expression)이다.
fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard" // 각 분기의 끝에 braek를 넣지 않아도 된다.
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDIGO -> "In"
            Color.VIOLET -> "Vain"
        }

// enum 상수를 임포트하면 이름만으로 사용할 수 있다.
// 한 분기안에서 여러 값을 매치 패턴으로 사용할 수 있다.
fun getWarmth(color: Color) = when (color) {
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}

// 코틀린에서 when은 자바의 switch보다 훨씬 더 강력하다.
// 분기조건에 상수(enum 상수나 숫자 리터럴)만을 사용할 수 있는 자바 switch와 달리 코틀린 when의 분기 조건은 임의의 객체를 허용한다.
fun mix(c1: Color, c2: Color) = // 두 색을 혼합해서 다른 색을 만들 수 있는 경우를 나열한다.
        when(setOf(c1, c2)) { // 구현하기 위해 집합 비교를 사용한다.
            // 코틀린 표준 라이브러리에는 인자로 전달받은 여러 객체를 그 객체들을 포함하는 집합인 Set 객체로 만드는 setOf라는 함수가 있다.
            // 집합(Set)은 원소가 모여 있는 컬렉션으로, 각 원소의 순서는 중요하지 않다.
            // 여기서는 setOf(c1, c2)와 분기 조건에 있는 객체 사이를 매치할 때 동등성(equility)을 사용한다.
            // 그러므로 처음에는 setOf(c1,c2)와 setOf(RED, YELLOW)를 비교하고, 그 둘이 같지 않으면 계속 다음 분기의 조건 객체를 차례로 비교하는 식으로 작동한다.
            // when의 분기 조건 부분에 식을 넣을 수 있기 때문에 많은 경우 코드를 더 간결하고 아름답게 작성할 수 있다.
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }

fun createPerson() = Person("Bob", false).name
fun findBob() =
        // when의 괄호 안에서 변수를 선언하고 대입할 수 있으면 when식 안에서만 사용할 수 있는 변수가 생기므로, when문 밖의 네임스페이스가 더럽혀지는 일을 줄일 수 있다.
        when(val person = createPerson()) {
            "Bob" -> person.get(0)
            else -> throw Exception("There is no Bob")
        }

// 위의 mix() 함수가 비효율적임을 눈치 챈 사람도 있을 것이다. 이 함수는 호출될 때마다 함수 인자로 주어진 두 색이 when의 분기 조건에 있는 다른 두 색과 같은지 비교하기 위해 여러 Set 인스턴스를 생성한다.
// 보통은 이런 비효율성이 크게 문제가 되지 않지만, 이 함수가 자주 호출된다면 불필요한 가비지 객체가 늘어나는 것을 방지하기 위해 함수를 고쳐 쓰는 것이 낫다.
// 인자가 없는 when 식을 사용하면 불필요한 객체 생성을 막을 수 있다. 코드는 약간 어려워지지만 성능을 더 향상시키기 위해 그정도 비용을 감수해야 하는 경우도 자주 있다.
fun mixOptimized(c1: Color, c2: Color) =
        when { // when에 아무 인자도 없다. when에 아무 조건이 없으려면 각 분기의 조건이 불리언 결과를 계산하는 식이어야 한다.
            // 추가 객체를 만들지 않는다는 장점이 있지만 가독성은 떨어진다.
            (c1 == RED && c2 == YELLOW) ||
                    (c1 == YELLOW && c2 == RED) -> ORANGE
            (c1 == YELLOW && c2 == BLUE) ||
                    (c1 == BLUE && c2 == YELLOW) -> GREEN
            (c1 == BLUE && c2 == VIOLET) ||
                    (c1 == VIOLET && c2 == BLUE) -> INDIGO
            else -> throw Exception("Dirty Color")
        }



fun main(args: Array<String>) {
    println(Color.BLUE.rgb())
    println(getMnemonic(Color.BLUE))
    println(mix(BLUE, YELLOW))
    println(findBob())
    println(mixOptimized(BLUE, YELLOW))
}