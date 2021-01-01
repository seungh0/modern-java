package com.ch19;

public class TrainJourneyUtils {

	/**
	 * link를 호출하면 firstJourney가 secondJourney를 포함하면서 파괴적인 갱신이 일어난다. (firstJourney를 변경시킴)
	 * => firstJourney에 의존하는 코드가 동작하지 않게 된다.
	 */
	public static TrainJourney link(TrainJourney a, TrainJourney b) {
		if (a == null) {
			return b;
		}
		TrainJourney t = a;
		while (t.onward != null) {
			t = t.onward;
		}
		t.onward = b;
		return a;
	}

	/**
	 * 함수형에서는 이 같은 부작용을 수반하는 메소드를 제한하는 방식으로 문제를 해결.
	 * 계산 결과를 표현할 자료구조가 필요하면 기존의 자료구조를 갱신하지 않도록 새로운 자료구조를 만들어야 한다.
	 */
	public static TrainJourney append(TrainJourney a, TrainJourney b) {
		return a == null ? b : new TrainJourney(a.price, append(a.onward, b)); // 함수형이며, 기존 자료구조를 변경하지 않는다.
	}

}
