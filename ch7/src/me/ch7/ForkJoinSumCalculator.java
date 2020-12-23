package me.ch7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

	public static final long THRESHOLD = 10_000;

	private final long[] numbers;
	private final int start;
	private final int end;

	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}

	private ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
		leftTask.fork(); // ForkJoinPool의 다른 스레드로 새로 생성한 테스크를 비동기로 실행한다.
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);

		Long rightResult = rightTask.compute(); // 두번째 서브태스크를 동기 실행한다.
		Long leftResult = leftTask.join(); // 첫번째 서브태스크의 결과를 읽거나 아직 결과가 없으면 기다린다.
		return leftResult + rightResult; // 두 테스크의 결과를 조합한 값이 이 테스크의 결과₩
	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}

	public static void main(String[] args) {
		long result = forkJoinSum(100000);
		System.out.println(result);
	}

	private static long forkJoinSum(long n) {
		long[] numbers = LongStream.range(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		return new ForkJoinPool().invoke(task);
	}

}
