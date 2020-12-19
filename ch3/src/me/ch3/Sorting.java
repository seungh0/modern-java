package me.ch3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting {

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.addAll(Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED)
		));

		// 1단계: 코드 전달
		inventory.sort(new AppleComparator());

		// 2단계: 익명 클래스 사용
		inventory.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight() - o2.getWeight();
			}
		});

		// 3단계 : 람다 표현식 사용
		inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
		inventory.sort(Comparator.comparing(apple -> apple.getWeight()));

		// 4단계: 메소드 참조 사용
		inventory.sort(Comparator.comparing(Apple::getWeight));

		// 디폴트 메소드를 통해서 함수형 인터페이스에서 유틸리티 메소드 제공
		inventory.sort(Comparator.comparing(Apple::getWeight)
				.reversed());

		// 역정렬 => 같을 경우 Color로 정렬
		inventory.sort(Comparator.comparing(Apple::getWeight)
				.reversed()
				.thenComparing(Apple::getColor));
	}

	// 1단계
	public static class AppleComparator implements Comparator<Apple> {
		@Override
		public int compare(Apple t1, Apple t2) {
			return t1.getWeight() - t2.getWeight();
		}
	}

}
