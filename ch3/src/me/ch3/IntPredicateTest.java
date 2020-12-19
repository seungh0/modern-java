package me.ch3;

public class IntPredicateTest {

	public static void main(String[] args) {
		IntPredicate evenNumbers = (int i) -> i % 2 == 0;
		System.out.println(evenNumbers.test(1000));
	}

	/**
	 * 제네릭 파라미터에는 참조형만 사용할 수 있다. (Consumer<T>)
	 * 자바 기본형 -> 참조형으로 변환 하는 기능 (박싱, 반대는 언박싱)
	 *
	 * but..! 변환 과정은 비용이 소모된다.
	 * 박싱한 값을 기본형을 감싸는 래퍼며 힙에 저장됨.
	 * 따라서 박싱한 값은 메모리를 더 소비하며, 기본형을 가져올 때도 메모리를 탐색하는 과정이 필요.
	 *
	 * 따라서 IntPredicate 등 함수형 인터페이스를 따로 제공.
	 */
	
	public interface IntPredicate {
		boolean test(int t);
	}
}
