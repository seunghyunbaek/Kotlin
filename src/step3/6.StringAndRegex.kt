package step3

// 이제는 확장을 통해 문자열과 정규식을 더 편리하게 다루는 방법을 살펴본다.

// 코틀린 문자열은 자바 문자열과 같다.
// 특별한 변환도 필요 없고 자바 문자열을 감싸는 별도의 래퍼(wrapper)도 생기지 않는다.

// 코틀린은 다양한 확장 함수를 제공함으로써 표준 자바 문자열을 더 즐겁게 다루게 해준다.
// 또한 혼동이 야기될 수 있는 일부 메소드에 대해 더 명확한 코틀린 확장 함수를 제공함으로써 프로그래머의 실수를 줄여준다.

// 첫 번째 예제로 문자열을 구분 문자열에 따라 나누는 작업을 코틀린에서 어떻게 처리하는지 살펴보자.
// 문자열 나누기
// 자바 개발자라면 String의 split 메소드를 잘 알고 있을 것이다. 모든 자바 개발자가 그 메소드를 사용하지만 불만을 표시하는 사람도 있다.
// "자바 split 메소드로는 점(.)을 사용해 문자열을 분리할 수 없습니다." 라는 질문이 있다.
// "12.345-6.A".split(".") 라는 호출의 결과가 [12, 345-6, A] 배열이라고 생각하는 실수를 저지르는 개발자가 많다.
// 하지만 자바의 split 메소드는 빈 배열을 반환한다! split의 구분 문자열은 실제로는 정규식(regular expression)이기 때문이다.
// 따라서 마침표(.)는 모든 문자를 나타내는 정규식으로 해석된다.

// 코틀린에서는 자바의 split 대신에 여러 가지 다른 조합의 파라미터를 받는 split 확장 함수를 제공함으로써 혼동을 야기하는 메소드를 감춘다.
// 정규식을 파라미터로 받는 함수는 String이 아닌 Regex 타입의 값을 받는다. 따라서 코틀린에서는 split 함수에 전달하는 값의 타입에 따라 정규식이나 일반 텍스트 중 어느 것으로 문자열을 분리하는지 쉽게 알 수 있다.

// 다음 코드는 마침표나 대시(-)로 문자열을 분리하는 예이다. (main)

// 코틀린 정규식 문법은 자바와 똑같다. 여기 있는 패턴은 마침표나 대시와 매치된다(정규식 안에서 마침표가 와일드카드(wild card)문자가 아닌 문자 자체(literal)로 쓰이게 하기 위해 마침표를 이스케이프(escape)시켰다).
// 정규식을 처리하는 API는 표준 자바 라이브러리 API와 비슷하지만 좀 더 코틀린답게 변경됐다.
// 예를 들어 코틀린에서는 toRegex 확장 함수를 사용해 문자열을 정규식으로 변경할 수 있다.
fun main(args: Array<String>) {
    println("12.345-6.A".split("\\.|-".toRegex())) // 정규식을 명시적으로 만든다.

    // 이런 간단한 경우에는 꼭 정규식을 쓸 필요가 없다. split 확장 함수를 오버로딩한 버전 중에는 구분 문자열을 하나 이상 인자로 받는 함수가 있다.
    println("12.345-6.A".split(".","-")) // 여러 구분 문자열을 지정한다.
    // 이렇게 여러 문자를 받을 수 있는 코틀린 확장 함수는 자바에 있는 단 하나의 문자만 받을 수 있는 메소드를 대신한다.

    main2()
}

// 정규식과 3중 따옴표로 묶은 문자열
// 다른 예로 두 가지 다른 구현을 만들어보자.
// 첫 번째 구현은 String을 확장한 함수를 사용하고, 두 번쨰 구현은 정규식을 사용한다.
// 우리가 할 일은 파일의 전체 경로명을 디렉터리, 파일 이름, 확장자로 구분하는 것이다.
// 코틀린 표준 라이브러리에는 어떤 문자열에서 구분 문자열이 맨 나중(또는 처음)에 나타난 곳 뒤(또는 앞)의 부분 문자열을 반환하는 함수가 있다.
// 이런 함수를 사용해 경로 파싱을 구현한 버전은 다음과 같다.

// "/Users/yole/kotlin-book/chapter.adoc"
// 디렉터리 (전체 경로의 첫 글자부터 마지막 슬래시 바로 전까지) : /Users/yole/kotlin-book
// 마지막 슬래시 : /
// 파일 이름 : chapter
// 마지막 마침표 : .
// 확장자 (마지막 마침표 다음부터 전체 경로의 마지막 문자까지) : adoc

// substringBeforeLast와 substringAfterLast 함수를 활용해 경로를 디렉터리, 파일 이름, 확장자 부분으로 나누기
fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $directory, name: $fileName, ext: $extension, fullName: $fullName")
}

// 코틀린에서는 정규식을 사용하지 않고도 문자열을 쉽게 파싱할 수 있다.
// 정규식은 강력하기는 하지만 나중에 알아보기 힘든 경우가 많다. 정규식이 필요할 때는 코틀린라이브러리를 사용하면 더 편하다.
fun parsePathRegex(path: String) {
    // 이 예제에서는 3중 따옴표 문자열을 사용해 정규식을 썼다.
    // 3중 따옴표 문자열에서는 역슬래시(\)를 포함한 어떤 문자도 이스케이프할 필요가 없다.
    // 예를 들어 일반 문자열을 사용해 정규식을 작성하는 경우 마침표 기호를 이스케이프하려면 \\. 라고 써야 하지만,
    // 3중 따옴표 문자열에서는 \. 라고 쓰면 된다. 이 예제에서 쓴 정규식은 슬래시와 마침표를 기준으로 경로를 세 그룹으로 분리한다.
    // 패턴 .은 임의의 문자와 매치될 수 있다. 따라서 첫 번째 그룹인 (.+)는 마지막 슬래시까지 모든 문자와 매치된다. 이 부분 문자열에는 마지막 슬래시를 제외한 모든 슬래시도 들어간다.
    // 비슷한 이유로 두 번째 그룹에도 마지막 마침표 전까지 모든 문자가 들어간다.
    // 세 번째 그룹에는 나머지 모든 문자가 들어간다.
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}
fun main2() {
    parsePath("/Users/yole/kotlin-book/chapter.adoc")
    parsePathRegex("/Users/yole/kotlin-book/chapter.adoc")
}