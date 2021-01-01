package com.ch19;

public class TrainJourney {

	public int price;
	public TrainJourney onward;

	public TrainJourney(int p, TrainJourney t) {
		price = p;
		onward = t;
	}

	@Override
	public String toString() {
		return String.format("TrainJourney[%d] -> %s", price, onward);
	}
	
}
