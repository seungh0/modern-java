package me.ch16;

import java.util.concurrent.*;

public class BestPriceFinder {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Double> future = executor.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				// dosomething
				return 0.0;
			}
		});
		Double db = 2.5;
		try {
			Double result = future.get(1, TimeUnit.SECONDS);
			System.out.println(result + db);
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
