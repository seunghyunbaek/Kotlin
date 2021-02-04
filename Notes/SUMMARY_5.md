# 람다로 프로그래밍
- 람다 식과 멤버 참조
- 함수형 스타일로 컬렉션 다루기
- 시퀀스: 지연 컬렉션 연산
- 자바 함수형 인터페이스를 코틀린에서 사용
- 수신 객체 지정 람다 사용

람다 식(lambda expression) 또는 람다는 기본적으로 다른 함수에 넘길 수 있는 작은 코드 조각을 뜻한다.  
람다를 사용하면 쉽게 공통 코드 구조를 라이브러리로 뽑아낼 수 있다.  
람다를 자주 사용하는 경우로 컬렉션 처리를 들 수 있다.

## 람다 식과 멤버 참조 
#### 람다 소개: 코드 블록을 함수 인자로 넘기기
람다 식을 사용하면 함수를 선언할 필요가 없고 코드 블록을 직접 함수의 인자로 전달할 수 있다.
```java
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        /* 수행할 동작 */
    }
});
```  
```kotlin
button.setOnClickListener { /* 수행할 동작 */ }
```
이 예제는 람다를 메소드가 하나뿐인 무명 객체 대신 사용할 수있다는 사실을 보여준다.

#### 람다와 컬렉션
코드에서 중복을 제거하는 것은 프로그래밍 스타일을 개선하는 중요한 방법 중 하나다.  
람다가 없다면 컬렉션을 편리하게 처리할 수 있는 좋은 라이브러릴르 제공하기 힘들다.  
```kotlin
data class Person(val name: String, val age: Int)

fun findOldest(people: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }

    println(theOldest)
}

val people = listOf(Person("Alice", 29), Person("Bob"), 31)
findOldest(people)

println(people.maxBy { it.age })
```
경험이 많은 개발자라면 findOldest 함수를 금방 만들 수 있을 것이다. 그러나 코틀린에는 더 좋은 방법이 있다. 라이브러리 함수를 쓰면 된다.

모든 컬렉션에 maxBy 함수를 호출할 수 있다.  
maxBy는 가장 큰 원소를 찾기 위해 비교에 사용할 값을 돌려주는 함수를 인자로 받는다. { it.age }는 비교에 사용할 값을 돌려주는 함수다.  
이런식으로 단지 함수나 프로퍼티를 반환하는 역할을 수행하는 람다는 멤버 참조로 대치할 수 있다.
```kotlin
people.maxBy(Person::age)
```
#### 람다 식의 문법
람다는 값처럼 여기저기 전달할 수 있는 동작의 모음이다. 람다를 따로 선언해서 변수에 저장할 수도 있다. 하지만 함수에 인자로 넘기면서 바로 람다를 정의하는 경우가 대부분이다.

```kotlin
val sum = { x: Int, y: Int -> x + y }
println(sum(1, 2)) // 3
```
코틀린 람다 식은 항상 중괄호로 둘러싸여 있다. 인자 목록 주변에 괄호가 없다는 사실을 꼭 기억하라.  
람다가 저장된 변수를 다른 일반 함수와 마찬가지로 다룰 수 있다.  
코드의 일부분을 불록으로 둘러싸 실행할 필요가 있다면 run을 사용한다.  
run은 인자로 받은 람다를 실행해주는 라이브러리 함수다.
```kotlin
run { println(42) }
```
실행 시점에 코틀린 람다 호출에는 아무 부가 비용이 들지 않으며, 프로그램의 기본 구성 요소와 비슷한 성능을 낸다.  
코틀린이 코드를 줄여 쓸 수 있게 제공했던 기능을 제거하고 정식으로 람다를 작성하면 다음과 같다.
```kotlin
people.maxBy({ p: Person -> p.age })
```
이 코드는 번잡하다. 우선 구분자가 너무 많아 가독성이 떨어진다. 그리고 컴파일러가 문맥으로부터 유추할 수 있는 인자 타입을 굳이 적을 필요는 없다. 마지막으로 인자가 단 하나뿐인 경우 굳이 인자에 이름을 붙이지 않아도 된다.

그럼 개선을 적용해보자.  
코틀린에는 함수 호출 시 맨 뒤에 있는 인자가 람다 식이면 그 람다를 괄호 밖으로 빼낼 수 있다는 문법 관습이 있다.
```kotlin
people.maxBy() {p: Person -> p.age } // 람다가 어떤 함수의 유일한 인자이고 괄호 뒤에 람다를 썼다면 호출 시 빈 괄호를 없애도 된다. 

people.maxBy {p: Person -> p.age} 
```
로컬 변수처럼 컴파일러는 람다 파라미터의 타입도 추론할 수 있다.