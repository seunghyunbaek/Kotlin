package step3

// 코드 다듬기 : 로컬 함수와 확장
// 많은 개발자들이 좋은 코드의 중요한 특징 중 하나가 중복이 없는 것이라 믿는다.
// 그래서 그 원칙에는 반복하지 말라(DRY, Don't Repeat Yourself)라는 이름도 붙어있다.
// 하지만 자바 코드를 작성할 때는 DRY 원칙을 피하기는 쉽지 않다.
// 많은 경우 메소드 추출(Extract Method) 리팩토링을 적용해서 긴 메소드를 부분부분 나눠서 각 부분을 재활용할 수 있다.
// 하지만 그렇게 코드를 리팩토링하면 클래스 안에 작은 메소드가 많아지고 각 메소드 사이의 관계를 파악하기 힘들어서 코드를 이해하기 더 어려워질 수도 있다.
// 리팩토링을 진행해서 추출한 메소드를 별도의 내부 클래스(inner class)안에 넣으면 코드를 깔끔하게 조직할 수는 있지만, 그에 따른 불필요한 준비 코드가 늘어난다.

// 코틀린에는 더 깔끔한 해법이 있다.
// 코틀린에서는 함수에서 추출한 함수를 원 함수 내부에 중첩시킬 수 있다. 그렇게 하면 문법적인 부가비용을 들이지 않고도 깔끔하게 코드를 조직할 수 있다.

// 흔히 발생하는 코드 중복을 로컬(local) 함수를 통해 어떻게 제거할 수 있는지 살펴보자.
class User(val id: Int, val name: String, val address: String)

// 사용자를 데이터베이스에 저장하는 함수가 있다. 이때 데이터베이스에 사용자를 저장하기 전에 각 필드를 검증해야 한다.
fun saveUser(user: User) { // 이 함수는 필드 검증이 중복된다.
    if (user.name.isEmpty()) { // 필드 검증
        throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
    }

    if (user.address.isEmpty()) { // 필드 검증
        throw IllegalArgumentException("Can't save user ${user.id}: empty Address")
    }

    // user를 데이터베이스에 저장한다.
}

// 여기서는 코드 중복이 그리 많지 않다. 하지만 클래스가 사용자의 필드를 검증할 때 필요한 여러 경우를 하나씩 처리하는 메소드로 넘쳐나기를 바라지는 않을 것이다.
// 이런 경우 검증 코드를 로컬 함수로 분리하면 중복을 없애는 동시에 코드 구조를 깔끔하게 유지할 수 있다.
fun saveUserLocal(user: User) {
    fun validate(user: User, // 한 필드를 검증하는 로컬 함수 정의
                 value: String,
                 fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }

    // 로컬 함수를 호출해서 각 필드를 검증한다.
    validate(user, user.name, "Name")
    validate(user, user.address, "Address")

    // user를 데이터베이스에 저장한다.
}

// 위의 코드가 훨씬 나아 보인다. 검증 로직 중복은 사라졌고, 필요하면 User의 다른 필드에 대한 검증도 쉽게 추가할 수 있다.
// 하지만 User 객체를 로컬 함수에게 하나하나 전댈해야 한다는 점은 아쉽다. 다행이지만 사실은 전혀 그럴 필요가 없다.
// 로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있다. 이런 성질을 이용해 불필요한 User 파라미터를 없애보자.

fun saveUserLocal2(user: User) {
    fun validate(value: String, fieldName: String) { // 이제 saveUser 함수의 user 파라미터를 중복 사용하지 않는다.
        if (value.isEmpty()) {
           throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName") // 바깥 함수의 파라미터에 직접 접근할 수 있다.
        }
    }

    validate(user.name, "Name")
    validate(user.address, "Address")

    // user를 데이터베이스에 저장한다.
}

// 이 예제를 더 개선하고 싶다면 검증 로직을 User 클래스를 확장한 함수로 만들 수도 있다.
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${id}: empty $fieldName")
        }
    }

    validate(name, "Name")
    validate(address, "Address")
}

// 위 처럼 코드를 확장 함수로 뽑아내는 기법은 놀랄 만큼 유용하다.
// User는 라이브러리에 있는 클래스가 아니라 여러분 자신의 코드 기바에 있는 클래스지만, 이 경우 검증 로직은 User를 사용하는 다른 곳에서는 쓰이지 않는 기능이기 때문에 User에 포함시키고 싶지는 않다.
// User를 간결하게 유지하면 생각해야 할 내용이 줄어들어서 더 쉽게 코드를 파악할 수 있다.
// 반면 한 객체만을 다루면서 객체의 비공개 데이터를 다룰 필요는 없는 함수는 위 코드 처럼 확장 함수로 만들면 객체.멤버처럼 수신 객체를 지정하지 않고도 공개된 멤버 프로퍼티나 메소드에 접근할 수 있다.
// 확장 함수를 로컬 함수로 정의할 수도 있다. 즉 User.validateBeforeSave를 saveUser 내부에 로컬 함수로 넣을 수 있다. 하지만 중첩된 함수의 깊이가 깊어지면 코드를 읽기가 상당히 어려워진다.
// 따라서 일반적으로는 한 단계만 함수를 중첩시키라고 권장한다.

// 지금까지 함수를 통해 할 수 있는 여러 멋진 기법을 살펴봤다.