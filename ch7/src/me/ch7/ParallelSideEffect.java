package me.ch7;

import java.util.stream.LongStream;

/**
 * 병렬 스트림과 병렬 계산에서는 공유된 가변 상태를 피해야 한다.
 */
public class ParallelSideEffect {

	public static void main(String[] args) {
		long result = sideEffectSum(10_000_000L); // 올바른 값이 나오지 않음 (50000005000000X)
		System.out.println(result);
	}

	private static long sideEffectSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n)
				.parallel()
				.forEach(accumulator::add); // 여러 스레드에서 동시에 누적자, (total += value)를 실행하면서 이러한 문제가 발생.
		return accumulator.total;
	}

	private static class Accumulator {
		public long total = 0; // total에 접근할 때 마다 (다수의 스레드에서 동시에 데이터에 접근하는) 데이터 레이스 문제가 발생한다.

		public void add(long value) {
			total += value;
		}
	}
}
