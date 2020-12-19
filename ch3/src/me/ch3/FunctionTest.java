package me.ch3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionTest {

	public static void main(String[] args) {
		List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), String::length);
		System.out.println(l);
	}

	public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for (T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}

	@FunctionalInterface
	public interface Function<T, R> {
		R apply(T t);
	}
}
