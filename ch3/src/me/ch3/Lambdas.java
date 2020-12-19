package me.ch3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;

public class Lambdas {

	public static void main(String[] args) {
		Runnable r = () -> System.out.println("Hello!");
		r.run();

		List<Apple> inventory = Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED)
		);

		List<Apple> greenApples = filter(inventory, (Apple a) -> a.getColor() == Color.GREEN);
		System.out.println(greenApples);

		Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

		inventory.sort(c);
		System.out.println(inventory);

		// 형식 추론
		Comparator<Apple> c1 = (a1, a2) -> a1.getWeight() - a2.getWeight();

		inventory.sort(c1);
		System.out.println(inventory);

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
		r1.run();

		// 기존의 람다 방식
		Comparator<Apple> c2 = (a1, a2) -> a1.getWeight() - a2.getWeight();

		// 메소드 참조
		Comparator<Apple> c3 = Comparator.comparingInt(Apple::getWeight);
	}

	private static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

	@FunctionalInterface
	public interface Predicate<T> {

		boolean test(T t);

	}

}
