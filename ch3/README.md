# ch3: 람다

- 람다 표현식은 익명 함수의 일종이다. 이름은 없지만, 파라미터 리스트, 바디, 반환 형식을 가지며 예외를 던질 수 있다.
- 람다 표현식으로 간결한 코드를 구현 가능
- 함수형 인터페이스는 하나의 추상 메소드만을 정의하는 인터페이스 (디폴트 메소드 제외)
- 함수형 인터페이스를 기대하는 곳에서만 람다 표현식을 사용할 수 있다.
- 람다 표현식을 이용해서 함수형 인터페이스의 추상 메소드를 즉석으로 제공할 수 있으며, 람다 표현식 전체가 함수형 인터페이스의 인스턴스로 취급된다.
- 람다 표현식의 기대 형식을 대상 형식이라고 한다.
- 메소드 참조를 이용하면 기존의 메소드 구현을 재상횽하고 직접 전달할 수 있다.
- Comparator, Predicate, Function 같은 함수형 인터페이스는 람다 표현식을 조합할 수 있는 다양한 디폴트 메소드를 제공한다.

# 자바 함수형 인터페이스

- Predicate<T> T - > boolean

```java
(List<String> list->list.isEmpty()
```

- Consumer<T> T -> void

```java
(Apple a)->System.out.println(a.getWeight())
```

- Function<T, R> T -> R

```java
(String s)->s.length
```

- Supplier<T> () -> T

```java
()->new Apple(10)
```

- UnaryOperator<T> T -> T

```java
(Integer i)->i+1
```

- BinaryOperator<T> (T, T) -> T

```java
(int a,int b)->a*b // IntBinaryOperator
```

- BiPredicate<T, U> (T, U) -> boolean

```java
(Apple a1,Apple a2)->a1.getWeight().compareTo(a2.getWeight())
```

- BiConsumer<T, U> (T, U) -> void
- BiFunction<T, U, R> (T, U) -> R

### 기본형 함수형 인터페이스

제네릭 파라미터에는 참조형만 사용할 수 있다. (Consumer<T->에서 T)

자바 기본형 -> 참조형으로 변환 하는 기능 (박싱, 반대는 언박싱)

but..! 변환 과정은 비용이 소모된다. 박싱한 값을 기본형을 감싸는 래퍼며 힙에 저장됨. 따라서 박싱한 값은 메모리를 더 소비하며, 기본형을 가져올 때도 메모리를 탐색하는 과정이 필요. 따라서
IntPredicate 등 함수형 인터페이스를 따로 제공.

### 형식 검사

람다가 사용되는 Context를 이용해서 람다의 형식을 추론할 수 있다. 어떤 콘텍스트에서 기대되는 람다 표현식의 형식을 대상 형식이라고 부른다.

```java
List<Apple> result=filter(inventory,(Apple apple)->apple.getWeight()>150);
```

1. 람다가 사용돈 콘텍스트는 무엇인가? 우선 filter의 정의를 확인하자 => filter(List<Apple> inventory, Predicate<Apple> p)

2. 대상 형식은 Predicate<Apple>이다. (T는 Apple로 대치됨.)

3. Predicate<Apple> 인터페이스의 추상 메소드는 무엇인가?    
   => boolean tet(Apple apple)

4. Apple을 인수로 받아 boolean을 반환하는 test 메소드다. => Apple -> boolean

5. 함수 디스크립터는 Apple -> boolean 이므로 람다의 시그니처와 일치한다. 람다도 Apple을 인수로 받아 boolean을 반환하므로 코드 형식 검사가 성공적으로 완료된다.

- 이러한 대상 형식이라는 특징 때문에 같은 람다 표현식이라도 호환되는 추상 메소드를 가진 다른 함수형 인터페이스로 사용될 수 있다.

  	int portNumber = 1337;
  	Runnable r1 = () -> System.out.println(portNumber);
  	/**
  	 * 자유 변수(파라미터로 넘겨진 변수가 아닌 외부에서 정외된 변수)를 활용할 수 있다. => 람다 캡처링
  	 * 제약 = 지역 변수는 final or final로 선언된 변수와 똑같이 사용 (불변)
  	 *
  	 * Why?
  	 * 우선 내부적으로 인스턴스 변수와 지역변수는 태생부터 다름.
  	 * 인스턴스 변수 -> 힙에 저장, 지역 변수 -> 스택에 위치
  	 * 람다에서 지역 변수에 바로 접근할 수 있다는 가정하에 람다가 스레드에서 실행된다면 변수를 할당한 스레드가 사라져서 변수 할당이 해제되었는데도 람다를
  	 * 실행하는 스레드에서는 해당 변수에 접근하려 할 수 있음. 따라서 자바 구현에서는 원래 변수에 접근을 허용하는 것이 아니라, 자유 지역 변수의
  	 * 복사본을 제공한다. 따라서 복사본의 값이 바뀌지 않아야 하므로 지역 변수에서는 한번만 값을 할당해야 한다는 제약이 생김
  	 */