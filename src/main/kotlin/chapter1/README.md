# Chapter1. Kotlin & Kotlin Standard Library

### Kotlin
> 코틀린은 JetBrains에서 개발한 멀티패러다임 프로그램 언어이다.
- 코틀린에 열광하는 이유
```
즉시 사용가능한 많은 유틸리티 클래스를 제공함
```

### Variables and data types
> 변수를 선언하는 방법 val, var
```kotlin
val name = "홍길동" // const, final 비슷
val country = "대한민국"
```
```text
모던 프로그래밍 언어에는 type inference(추론형식)가 있다.
```
> 변수 타입 지정
  
```kotlin
var score : Int
```
- Numbers: Double, Float, Long, Int, Short, Byte
- Characters: Char, String
- 기타: Boolean, Array

### Optionals and null-safety
> null을 할당 후 인스턴스 생성 방법
```kotlin
var car: Car? = null
car = Car("BMW")
```
> NPE(Null-Pointer Exception) 방지
```kotlin
car?.drive()
```
- car가 not null 일때만 실행한다.
```kotlin
val immutableCar: Car = car ?: Car("람보르기니")
```
1. immutableCar를 const로 생성한다.
2. immutableCar가 null이면 생성한다.
```kotlin
car!!.drive()
```
- Null 인지 확인
```kotlin
@Test
fun test(){
	var car: Car? = null
	val immutableCar: Car = car ?: Car("Porche")
	immutableCar?.drive()
	immutableCar!!.drive()
	assertThrows<NullPointerException> {
		car!!.drive()
	}
}
```

### Conditional statements
> When 절
```kotlin
val groupSize = 3
when(groupSize){
    1 -> println("Single")
    2 -> println("Pair")
    3 -> {
        println("Trio")
    }
    else -> println("")
}
- Switch-Case 문과 유사
```

### Loops
```kotlin
for(i in 1..3){
    println(i)
}

for(item in collection) println(item)
```

### Functions
```kotlin
fun max(a:Int, b:Int):Int {
    return if(a > b) a else b
}
```

### Generics
```kotlin
class Box {
    var content: Any? = null
    
    fun put(content: Any?){
        this.content = content
    }
    
    fun retrieve():Any? {
        return content
    }
    
    fun isEmpty():Boolean {
        return content == null
    }
}
```
```kotlin
class Box<T> {
    var content:T? = null
    
    fun put(content:T?){
        this.content = content
    }
    
    fun retrieve():T? {
        return content
    }
    
    fun isEmpty():Boolean {
        return content == null
    }
}
val box = Box<Int>()
box.put(4)
val boolBox = Box<Boolean>()
boolBox.put(true)
boolBox.isEmpty()
```

### Package kotlin
```kotlin
inline fun<T, R> with(receiver:T, block:T.() -> R):R {
	return receiver.block()
}
```
```kotlin
class Person {
	var name:String? = null
	var age:Int? = null
}

val person:Person = getPerson()
println(person.name)
println(person.age)

with(person){
	println(name)
	println(age)
}

val.person:Person = getPerson().also{
	println(it.name)
	println(it.age)
}
```

### with, run, apply, also, let Examples
- apply
```text
블록에서 함수를 사용하지 않고 자신을 다시 반환하는 경우 사용
ex) 
- 객체의 프로퍼티만 사용하는경우
- 특정객체를 생성함과 동시에 초기화
```
```kotlin
var clark = Person()
clark.name = "Clark"
clark.age = 18
=> 
val peter = Person().apply {
	name = "Peter"
	age = 18
}
```
- let
```text
이미 생성된 객체
ex)
- 지정된 값이 null이 아닌경우 코드를 실행할때
- null 객체를 다른 null 객체로 반환하는 경우
- 단일 지역 변수의 범위를 제한하는 경우
```
```kotlin
val person:Person? = getPromotablePerson()
if(person != null) {
	promote(person)
}
=>
getNullablePerson()?.let{
	promote(it)
}

val driver:Person? = getDriver()
val driversLicence:Licence? = if(driver == null) null else
		licenceService.getDriversLicence(it)
=>
val driversLicence:Licence? = getNullablePerson()?.let{
	licenceService.getDriversLicence(it)
}

val person:Person = getPerson()
val personDao:PersonDao = getPersonDao()
personDao.insert(person)
=>
val person:Person = getPerson()
getPersonDao().let{ dao ->
	dao.insert(person)
}
```

- with
```text
non-nullable 수신객체, 결과가 필요하지 않은 경우 사용
run과 기능은 동일하나 리시버 전달할 객체가 어디에 위치하는지
```
```kotlin
val person:Person = getPerson()
print(person.name)
print(person.age)
=>
val person:Person = getPerson()
with(person){
	print(name)
	print(age)
}

recyclerView?.let {
    with(it) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = this@MainActivity.adapter
    }
}
```

- run
```text
어떤값을 계산할 필요가 있거나 지역변수의 번위를 제한하려할때
apply는 객체를 생성함과 동시에 연속된 작업 수행,
run은 이미 생성된 객체의 메서드나 필드를 연속적으로 호출할때
```
```kotlin
val person:Person = getPerson()
val personDao:personDao = getPersonDao()
val inserted:Boolean = personDao.insert(person)
=>
val inserted:Boolean = run {
	val person:Person = getPerson()
	val personDao:PersonDao = getPersonDao()
	personDao.insert(person)
}

recyclerView?.run {
    layoutManager = LinearLayoutManager(this@MainActivity)
    adapter = this@MainActivity.adapter
}
```

### 체인 사용
```kotlin
private fun insert(user: User) = SqlBuilder().apply {
  append("INSERT INTO user (email, name, age) VALUES ")
  append("(?", user.email)
  append(",?", user.name)
  append(",?)", user.age)
}.also {
  print("Executing SQL update: $it.")
}.run {
  jdbc.update(this) > 0
}
```

### List
```kotlin
val places = listOf("Paris", "London")
val mutablePlace = mutableListOf("Paris", "London")
```

### Map
```kotlin
val scores = mutableMapOf("Eric" to 9, "Mark" to 12, "Wayne" to 1)
scores["Wayne"] = 5
```

### Mutable vs Immutable
```text
mutable : 가변 객체
immutable 불변객체
```