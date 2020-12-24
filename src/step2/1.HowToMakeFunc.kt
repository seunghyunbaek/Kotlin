package step2

// 블록이 본문인 함수
// 값을 반환한다면 반드시 반환 타입을 지정하고 return문을 사용해 반환 값을 명시해야 함.
fun maxBlockBody(a: Int, b:Int): Int {
    return if (a > b) a else b
}

// 식이 본문인 함수 (자주 사용 됨)
//fun maxExpressionBody(a: Int, b:Int): Int = if(a > b) a else b

// 반환 타입이 생략될 수 있는 이유는 식이 본문인 함수의 경우 굳이 사용자가 반환 타입을 적지 않아도 컴파일러가 함수 본문 식을 분석해서 식의 결과 타입을 함수 반환 타입으로 정해준다.
// 타입 추론(type inference)
// 식이 본문인 함수의 반환 타입만 생략 가능하다는 점에 유의
fun maxExpressionBody(a: Int, b:Int) = if(a > b) a else b

fun main(args: Array<String>) {

    println (maxBlockBody(1, 2))

    println(maxExpressionBody(1, 2))
}