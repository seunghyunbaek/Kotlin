package step2

import java.io.BufferedReader
import java.io.StringReader
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

// 코틀린의 예외(Exception)처리는 자바나 다른 언어의 예외 처리와 비슷하다.
// 함수는 정상적으로 종료할 수 있지만 오류가 발생하면 예외를 던질(throw) 수 있다.
// 함수를 호출하는 쪽에서는 그 예외를 잡아 처리할 수 있다. 발생한 예외를 함수 호출 단에서 처리(catch)하지 않으면 함수 호출 스택을 거슬러 올라가면서 예외를 처리하는 부분이 나올 때까지 예외를 다시 던진다(rethrow).

fun main(args: Array<String>) {
    val percentage = 1000
    if (percentage !in 0..100) {
        throw IllegalArgumentException( // 다른 클래스와 마찬가지로 예외 인스턴스를 만들 때도 new를 붙일 필요가 없다.
                "A percentage value must be between 0 and 100: $percentage"
        )
    }

    // 자바와 달리 코틀린의 throw는 식이므로 다른 식에 포함될 수 있다.
    val number = 1000
    val percentage2 =
            if (number in 0..100)
                number
            else
                throw IllegalArgumentException( // throw는 식이다.
                        "A percentage value must be between 0 and 100: $number")

    // 자바와 마찬가지로 예외를 처리하려면 try, catch, finally 절을 함께 사용한다.
    // 자바 코드와 가장 큰 차이는 throws(이 경우 s가 붙어있다) 절이 코드에 없다는 점이다.
    // 자바에서는 함수를 작성할 때 throws IOException을 붙여야 한다. 이유는 IOException이 체크 예외(checked exception)이기 때문이다.
    // 자바에서는 체크 예외를 명시적으로 처리해야 한다. 어떤 함수가 던질 가능성이 있는 예외나 그 함수가 호출한 다른 함수에서 발생할 수 있는 예외를 모두 catch로 처리해야 하며, 처리하지 않은 예외는 throws 절에 명시해야 한다.
    // 다른 최신 JVM 언어와 마찬가지로 코틀린도 체크 예외와 언체크 예외(unchecked exception)를 구별하지 않는다.
    fun readNumber(reader: BufferedReader): Int? { // 함수가 던질 수 있는 예외를 명시할 필요가 없다.
        try {
            val line = reader.readLine()
            return Integer.parseInt(line)
        }
        catch (e: NumberFormatException) {
            return null
        } finally {
            reader.close()
        }
    }
    // 위의 코드에서 NumberFormatException은 체크 예외가 아니다. 따라서 자바 컴파일러는 NumberFormatException을 잡아내게 강제하지 않는다.
    // 동시에 BufferedReader.close는 IOException을 던질 수 있는데, 이 예외는 체크 예외이므로 자바에서는 반드시 처리해야 한다.
    // 하지만 실제 스트림을 닫다가 실패하는 경우 특별히 스트림을 사용하는 클라이언트 프로그램이 취할 수 있는 의미 있는 동작은 없다.
    // 그러므로 이 IOException을 잡아내는 코드는 불필요하다.
    // 자바 7의 자원을 사용하는 try-with-resource는 어떨까? 코틀린은 그런 경우를 위한 특별한 문법을 제공하지 않는다. 하지만 라이브러리 함수로 같은 기능을 구현한다.

    // try를 식으로 사용하기
    // 코틀린의 try 키워드는 if나 when과 마찬가지로 식이다. 따라서 try의 값을 변수에 담을 수 있다. if와 달리 try의 본문을 반듯 중괄호로 둘러싸야한다.
    // catch 블록에서 return을 사용함으로 예외가 발생한 경우 이후 문장은 실행되지 않는다.
    fun readNumber2(reader: BufferedReader) {
        val number = try {
            Integer.parseInt(reader.readLine())
        } catch (e: NumberFormatException) {
            return
        }

        println(number)
    }
    val reader = BufferedReader(StringReader("not a number"))
    readNumber2(reader)
}