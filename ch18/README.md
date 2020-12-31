# 18. 함수형 관점으로 생각하기

## 18-1 시스템 구현과 유지보수

- 부작용 없음(no side effect) + 불변성 (immutability)

### 함수형 프로그램이란?

- 함수형? => 수학의 함수처럼 부작용이 없는
- 함수란, 0개 이상의 인수를 가지며 한개 이상의 결과를 반환하지만, 부작용이 없어야 한다.
- 부작용 없는 메소드란, 자신을 포함하는 클래스의 상태 그리고 다른 객체의 상태를 바꾸지 않으며 return 문을 통해서만 자신의 결과를 반환하는 메소드
    - 부작용의 예
        - 자료구조를 고치거나 필드에 값으 할당
        - 예외 발생
        - 파일에 쓰기 등의 I/O 동작 수행

### 참조 투명성

- 같은 인수로 함수를 호출했을 떄 항상 같은 결과를 반환한다면 참조적으로 투명한 함수라고 표현.
- 참조 투명성은 프로그램 이해에 큰 도움을 준다. 또한 참조 투명성은 비싸거나 오랜 시간이 걸리는 연산을 기억화 또는 캐싱을 통해 다시 계산하지 않고, 저장하는 최적화 기능도 제공

```java
public class FactorialMain {

	public static void main(String[] args) {
		System.out.println(factorialIterative(5));
		System.out.println(factorialRecursive(5));
		System.out.println(factorialStreams(5));
		System.out.println(factorialTailRecursive(5));
	}

	/**
	 * 순수 함수형 프로그래밍 언어에서는 반복문을 포함하지 않음. => 이러한 반복문 때문에 변화가 자연스럽게 코드에 스며들 수 있음.
	 */
	private static int factorialIterative(int n) {
		int r = 1;
		for (int i = 1; i <= n; i++) {
			r *= i;
		}
		return r;
	}

	/**
	 * 반복문과는 달리 재귀를 이용하면 변화가 일어나지 않는다.
	 * 재귀를 이용하면 루프 단계마다 갱신되는 반복 변수를 제거할 수 있다.
	 *
	 * 일반적으로 반복코드보다 재귀 코드가 더 비싸다. Why?
	 * factorialRecursive 함수를 호출할 때마다 호출 스택에 각 호출시 생성되는 정보를 저장할 새로운 스택 프레임일 만들어진다.
	 * 즉 재귀 팩토리얼의 입력값에 비례해서 메모리의 사용량이 증가한다.
	 * => 꼬리 호출 최적화로 해결
	 */
	private static long factorialRecursive(long n) {
		return n == 1 ? 1 : n * factorialRecursive(n - 1);
	}

	/**
	 * 중간 결과를 각각의 스택 프레임으로 저장해야 하는 일반 재귀와 달리 꼬리 재귀에서는 컴파일러가 하나의 스택 프레임을 재활용할 가능성이 생김.
	 * 안타깝게 자바는 이와 같은 최적화를 제공하지 않음 => 그러나 고전적인 재귀보다 여러 컴파일러 최적화 여지를 남겨둘 수 있는 꼬리 재귀를 적용하는 것이 좋다.
	 */
	private static long factorialTailRecursive(long n) {
		return factorialHelper(1, n);
	}

	private static long factorialHelper(long acc, long n) {
		return n == 1 ? acc : factorialHelper(acc * n, n - 1);
	}

	/**
	 * 자바 8 에서는 반복을 스트림으로 대체해서 변화를 피할 수 있다. 또한 반복을 재귀로 바꾸면 더 간결하고, 부작용이 없는 알고리즘을 만들 수 있다.
	 */
	private static long factorialStreams(long n) {
		return LongStream.rangeClosed(1, n)
				.reduce(1, (long a, long b) -> a * b);
	}

}
```

### 정리

- 공유된 가변 자료구조를 줄이는 것이 장기적으로 프로그램을 유지보수하고 디버깅하는데 도움이 된다.
- 함수형 프로그래밍은 부작용이 없는 메소드와 선언형 프로그래밍 방식을 지향한다.
- 함수형 메소드는 입력 인수와 출력 결과만을 갖는다.
- 같은 인수값으로 함수를 호출했을 떄 항상 같은 값을 반환하면 참조 투명성을 갖는 함수다. (반복문은 재귀로 대체할 수 있다.)
