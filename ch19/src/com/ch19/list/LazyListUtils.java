package com.ch19.list;

public class LazyListUtils {

	public static LazyList<Integer> from(int n) {
		return new LazyList<>(n, () -> from(n + 1));
	}

	public static MyList<Integer> primes(MyList<Integer> numbers) {
		return new LazyList<>(
				numbers.head(),
				() -> primes(
						numbers.tail()
								.filter(n -> n % numbers.head() != 0)
				)
		);
	}

	public static void main(String[] args) {
		LazyList<Integer> numbers = from(2);
		int two = numbers.head();
		int three = numbers.tail().head();
		int four = numbers.tail().tail().head();
		System.out.println(two + ", " + three + ", " + four);

		printAll(primes(from(2)));
	}

	private static <T> void printAll(MyList<T> list) {
		while (!list.isEmpty()) {
			System.out.println(list.head());
			list = list.tail();
		}
	}

}
