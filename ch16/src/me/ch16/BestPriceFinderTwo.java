package me.ch16;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class BestPriceFinderTwo {

	private final List<Shop> shops = Arrays.asList(
			new Shop("BestPrice"),
			new Shop("LetsSaveBig"),
			new Shop("MyFavoriteShop"),
			new Shop("BuyItAll"));

	public List<String> findPricesSequential(String product) {
		return shops.stream()
				.map(shop -> String.format("%s prices is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}

	public List<String> findPrices(String product) {
		return shops.parallelStream()
				.map(shop -> String.format("%s prices is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}

	public List<String> findPricesFuture(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
				.collect(Collectors.toList());
		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
		// 두 map 연산을 하나의 스트림 처리 파이프라인으로 처리하지 않고 두 개의 스트림 파이프라인으로 처리 => 스트림 연산은 게으른 특성이 있으므로 하나의 파이프라인으로 연산을 처리했다면 모든 가격 정보 요청 동작이 동기적, 순차적으로 이루어짐.
	}

	private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	});

}
