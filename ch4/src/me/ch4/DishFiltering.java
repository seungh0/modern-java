package me.ch4;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static me.ch4.Dish.menu;

public class DishFiltering {

	public static void main(String[] args) {
		// 자바 7의 기존의 코드
		List<Dish> lowCaloricDishes = new ArrayList<>();
		for (Dish dish : menu) {
			if (dish.getCalories() < 400) {
				lowCaloricDishes.add(dish);
			}
		}
		Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return Integer.compare(o1.getCalories(), o2.getCalories());
			}
		});
		List<String> lowCaloricDishesNames = new ArrayList<>();
		for (Dish dish : lowCaloricDishes) {
			lowCaloricDishesNames.add(dish.getName());
		}
		System.out.println(lowCaloricDishesNames);

		// 자바 8 스트림 코드
		List<String> lowCaloricDishesName1 = menu.stream()
				.filter(d -> d.getCalories() < 400)
				.sorted(comparing(Dish::getCalories))
				.map(Dish::getName)
				.collect(Collectors.toList());
		System.out.println(lowCaloricDishesName1);

		// 멀티코어 아키텍처에서 병렬로 실행
		List<String> lowCaloricDishesName2 = menu.parallelStream()
				.filter(d -> d.getCalories() < 400)
				.sorted(comparing(Dish::getCalories))
				.map(Dish::getName)
				.collect(Collectors.toList());
		System.out.println(lowCaloricDishesName2);

		List<String> names = menu.stream()
				.filter(dish -> {
					System.out.println("filtering " + dish.getName());
					return dish.getCalories() < 300;
				})
				.map(dish -> {
					System.out.println("mapping " + dish.getName());
					return dish.getName();
				})
				.limit(2)
				.collect(toList());
		System.out.println(names);

//		filtering pork
//		filtering beef
//		filtering chicken
//		filtering french fries
//		filtering rice
//		filtering season fruit
//		mapping season fruit
//		filtering pizza
//		filtering prawns
//		filtering salmon
//		[season fruit]
	}

}
