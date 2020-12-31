package com.ch17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class TempSubscription implements Flow.Subscription {

	private final Flow.Subscriber<? super TempInfo> subscriber;
	private final String town;

	private static final ExecutorService executor = Executors.newSingleThreadExecutor();

	public TempSubscription(Flow.Subscriber<? super TempInfo> subscriber, String town) {
		this.subscriber = subscriber;
		this.town = town;
	}

	@Override
	public void request(long n) {
		executor.submit(() -> {
			for (long i = 0L; i < n; i++) { // Subscriber 가 만든 요청을 한 개씩 반복
				try {
					subscriber.onNext(TempInfo.fetch(town)); // 현재의 온도를 Subscriber 로 전달
				} catch (Exception e) {
					subscriber.onError(e); // 온도 가져오기를 실패하면 Subscriber 로 에러를 전달
					break;
				}
			}
		});
	}

	@Override
	public void cancel() {
		subscriber.onComplete(); // 구독이 취소되면 완료 신호를 Subscriber 로 전달
	}

}
