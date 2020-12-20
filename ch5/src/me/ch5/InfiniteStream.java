package me.ch5;

import java.util.stream.Stream;

public class InfiniteStream {

	public static void main(String[] args) {
		// 무한 스트림 (언바운드 스트림)
		Stream.iterate(0, n -> n + 2)
				.limit(10)
				.forEach(System.out::println);

		// 피보나치 수열
		Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
				.limit(20)
				.forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));
	}

}
