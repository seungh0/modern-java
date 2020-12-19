package me.ch3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
