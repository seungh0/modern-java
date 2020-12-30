package me.ch16;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

	private String name;

	public Shop(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getPrice(String product) {
		return calculatePrice(product);
	}

	private double calculatePrice(String product) {
		delay();
		return product.charAt(0) + product.charAt(1);
	}

	private static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>(); // 계산 결과를 포함할 CompletableFuture을 생성한다.
		new Thread(() -> {
			try {
				double price = calculatePrice(product); // 다른 스레드에서 비동기적으로 계산을 수행한다.
				futurePrice.complete(price); // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정한다.
			} catch (Exception e) {
				futurePrice.completeExceptionally(e); // 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future을 종료.
			}
		}).start();
		return futurePrice; // 계산 결과가 완료되길 기다리지 않고 Future을 반환한다.
	}

	public Future<Double> getPriceAsyncFatory(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product)); // 팩토리 메소드
	}

	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsyncFatory("my favorite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after" + invocationTime + " msecs");

		System.out.println("====다른 작업 수행 =====");
		try {
			double price = futurePrice.get(); // 가격 정보가 있으면 Future에서 가격 정보를 읽고, 가격 정보가 없으면 가격 정보를 받을때 까지 블록.
			System.out.println("Price is " + price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returend after" + retrievalTime + " msecs");
	}

}
