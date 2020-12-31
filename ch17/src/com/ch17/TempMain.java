package com.ch17;

import java.util.concurrent.Flow;

public class TempMain {

	public static void main(String[] args) {
		getTemperatures("New York").subscribe(new TempSubscriber());
	}

	private static Flow.Publisher<TempInfo> getTemperatures(String town) {
		return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
	}

}
