package step2

fun main(args: Array<String>) {

    val question = "삶, 우주, 그리고 모든 것에 대한 궁극적인 질문"
    val answer = 42 // 타입을 생략하면 컴파일러가 초기화 식을 분석해서 초기화 식의 타입을 변수 타입으로 지정한다.
//    val answer: Int = 42 // 타입을 명시해도 된다.

    val yearsToCompute = 7.5e6 // 부동소수점(floating point) 상수를 사용한다면 변수 타입은 Double이 된다.

    val answer2:Int // 초기화 식이 없다면 변수에 저장될 값에 대해 아무 정보가 없기 때문에 컴파일러가 타입을 추론할 수 없다. 따라서 타입을 반드시 지정해야 한다.
    answer2 = 40

    // val 변수는 블록을 실행할 때 정확히 한 번만 초기화돼야 한다.
    // 하지만 어떤 블록이 실행될 때 오직 한 초기화 문장만 실행됨을 컴파일러가 확인할 수 있다면 조건에 따라 val 값을 다른 여러 값으로 초기화 할 수 있다.
    val message: String
    if (answer > answer2) {
        message = "Success"
    } else {
        message = "Failed"
    }

    // val 참조 자체는 불변일지라도 그 참조가 가리키는 객체의 내부 값은 변경될 수 있다.
    val language = arrayListOf("Java")
    language.add("Kotlin")

    // 변수의 값을 변경할 수는 있지만 변수의 타입은 고정돼 바뀌지 않는다. (type mismatch)
    // 타입이 컴파일러가 기대하는 타입과 다르기 때문이다.
    // 컴파일러는 변수 선언 시점의 초기화 식으로부터 변수의 타입을 추론하며, 선언 이후 재대입이 이뤄질 때는 이미 추론한 변수의 타입을 염두에 두고 대입문의 타입을 검사한다.
    var answer3 = 42
//    answer3 = "no answer" // 다른 타입의 값을 저장하고 싶다면 변환 함수를 써서 값을 변수의 타입으로 변환하거나, 값을 변수에 대입할 수 있는 타입으로 강제 형 변환(coerce) 해야한다.
}