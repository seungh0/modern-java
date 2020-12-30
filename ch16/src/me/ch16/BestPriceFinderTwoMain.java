package me.ch16;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderTwoMain {

	private static BestPriceFinderTwo bestPriceFinder = new BestPriceFinderTwo();

	public static void main(String[] args) {
		execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"));
		// 순차적으로 정보를 검색 => 각각 1초의 대기시간으로 전체 검색 결과는 4초 + @

		execute("parallel", () -> bestPriceFinder.findPrices("myPhone27S"));
		// 네 개의 상점에서 병렬로 검색이 진행되므로 1초 남짓의 시간에 검색이 완료

		execute("futures", () -> bestPriceFinder.findPricesFuture("myPhone27S"));
	}

	private static void execute(String msg, Supplier<List<String>> s) {
		long start = System.nanoTime();
		System.out.println(s.get());
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println(msg + " done in " + duration + " msecs");
	}

}
