package me.ch3;

import java.util.Arrays;
import java.util.List;

public class ConsumerTest {

	public static void main(String[] args) {
		forEach(Arrays.asList(1, 2, 3, 4, 5
		), System.out::println);
	}

	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T t : list) {
			c.accept(t);
		}
	}

	@FunctionalInterface
	public interface Consumer<T> {
		void accept(T t);
	}

}

