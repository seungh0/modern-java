package me.ch16;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderThreeMain {

	private static BestPriceFinderThree bestPriceFinder = new BestPriceFinderThree();

	public static void main(String[] args) {
		execute("sequential", () -> bestPriceFinder.findPrices("myPage"));
		// 순차적으로 다섯 상점에 가격 정보를 요청하느라 5초가 소요, 다섯 상점에서 반환한 가격 정보에 할인 코드를 적용할 수 있도록 할인 서비스에 5초가 소요

		// 병렬 스트림을 이용하면 성능을 쉽게 개선할 수 있다는 사실을 이미 확인 => but 병렬 스트림에서는 스트림이 사용하는 스레드 풀의 크기가 고정되어 있어서 상점 수가 늘어났을 때처럼 검색 대상이 확장되었을 때 유연하게 대응할 수 없음.
	}

	private static void execute(String msg, Supplier<List<String>> s) {
		long start = System.nanoTime();
		System.out.println(s.get());
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println(msg + " done in " + duration + " msecs");
	}

}
