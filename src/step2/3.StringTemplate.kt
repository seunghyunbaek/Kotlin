package step2

fun main(args: Array<String>) {
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name!")
    // 변수를 문자열 안에 사용할 수 있다.
    // $를 문자열에 넣고 싶으면 \를 사용해 $를 이스케이프(escape) 시켜야 한다. \$

    // 복잡한 식도 중괄호로 둘러싸서 문자열 템플릿 안에 넣을 수 있다.
    if(args.size > 0) {
        println("Hello, ${args[0]}!")
    }

    // 중괄호로 둘러싼 식 안에서 큰 따옴표를 사용할 수도 있다.
    println("Hello, ${if(args.size > 0) args[0] else "someone"}")
}