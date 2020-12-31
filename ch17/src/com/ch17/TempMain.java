package com.ch17;

import java.util.concurrent.Flow;

public class TempMain {

	public static void main(String[] args) {
		getTemperatures("New York").subscribe(new TempSubscriber());
		getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
	}

	private static Flow.Publisher<TempInfo> getTemperatures(String town) {
		return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
	}

	private static Flow.Publisher<TempInfo> getCelsiusTemperatures(String town) {
		return subscriber -> {
			TempProcessor processor = new TempProcessor();
			processor.subscribe(subscriber);
			processor.onSubscribe(new TempSubscription(processor, town));
		};
	}

}
