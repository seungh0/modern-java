package me.ch7;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

	public static void main(String[] args) {
		long result = iterativeSum(10);
		System.out.println(result);

		long result2 = sequentialSum(10);
		System.out.println(result2);

		long result3 = rangedSum(10);
		System.out.println(result3);
	}

	// 전통적인 자바 = 반복문으로 구현
	private static long iterativeSum(long n) {
		long result = 0;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}

	// 스트림
	private static long sequentialSum(long n) {
		return Stream.iterate(1L, i -> i + 1)
				.limit(n)
				.parallel() // 스트림을 병렬 스트림으로 변환 =  스트림이 여러 청크로 분할되어 있음.
//				.sequential() // 순차 스트림
				.reduce(0L, Long::sum);
	}

	private static long rangedSum(long n) {
		return LongStream.rangeClosed(1, n)
				.parallel()
				.reduce(0L, Long::sum);
	}

}
