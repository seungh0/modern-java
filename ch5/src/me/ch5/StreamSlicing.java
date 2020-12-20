package me.ch5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.ch5.Dish.menu;

public class StreamSlicing {

	public static void main(String[] args) {
		// 스트림 슬라이싱
		// 정렬되어 있다고 가정하에 takeWhile, dropWhile 사용
		List<Dish> dishes = menu.stream()
				.takeWhile(dish -> dish.getCalories() > 320)
				.collect(Collectors.toList());
		System.out.println(dishes);
		// [pork, beef, chicken, french fries, rice] 한 번 끊기기 전까지

		List<Dish> dishes2 = menu.stream()
				.dropWhile(dish -> dish.getCalories() > 320)
				.collect(Collectors.toList());
		System.out.println(dishes2);
		// [season fruit, pizza, prawns, salmon]

		// 스트림 축소
		List<Dish> limit = menu.stream()
				.filter(d -> d.getCalories() > 300)
				.limit(3)
				.collect(Collectors.toList());
		System.out.println(limit);

		// 요소 건너뛰기
		List<Dish> skip = menu.stream()
				.filter(d -> d.getCalories() > 300)
				.skip(2)
				.collect(Collectors.toList());
		System.out.println(skip);

		List<Integer> dishNameLengths = menu.stream()
				.map(Dish::getName)
				.map(String::length)
				.collect(Collectors.toList());
		System.out.println(dishNameLengths);

		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<int[]> pairs = numbers1.stream()
				.flatMap(i -> numbers2.stream()
						.map(j -> new int[]{i, j}))
				.collect(Collectors.toList());

		boolean anyMatch = menu.stream()
				.anyMatch(Dish::isVegetarian);
		System.out.println(anyMatch);

		boolean allMatch = menu.stream()
				.allMatch(Dish::isVegetarian);
		System.out.println(allMatch);

		boolean noneMatch = menu.stream()
				.noneMatch(Dish::isVegetarian);
		System.out.println(noneMatch);

		Optional<Dish> dish = menu.stream()
				.filter(d -> d.getCalories() > 300)
				.findAny();
		System.out.println(dish);

		Optional<Dish> dish2 = menu.stream()
				.filter(d -> d.getCalories() > 300)
				.findFirst();
		System.out.println(dish2);
		// 병렬 실행에서는 첫 번째 요소를 찾기 어렵다.
		// 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.
	}

}
