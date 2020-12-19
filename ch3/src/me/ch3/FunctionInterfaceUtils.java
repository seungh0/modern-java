package me.ch3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionInterfaceUtils {

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.addAll(Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED)
		));

		// Function Utils
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;
		Function<Integer, Integer> h = f.andThen(g); // g(f(x)) => (x+1) * 2
		int result = h.apply(1);
		System.out.println(result);

		Function<Integer, Integer> x = f.compose(g);
		System.out.println(x.apply(1)); // f(g(x)) => (x*2) + 1

		// Predicate Utils
		Predicate<Apple> redApple = (a) -> a.getColor() == Color.RED;
		Predicate<Apple> notRedApple = redApple.negate(); // 반전
		Predicate<Apple> redAndWeightApple = redApple
				.and((a) -> a.getWeight() >= 250)
				.or((a) -> a.getWeight() <= 150);
		System.out.println(redAndWeightApple.test(inventory.get(0)));
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

}
