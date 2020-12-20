package me.ch5;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.ch5.Dish.menu;

public class TransactionTest {

	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");

		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700),
				new Transaction(alan, 2012, 950)
		);

		// 문제1: 2011년에 일어나 모든 트랜잭션을 찾아 값을 오름차순으로 정리
		List<Transaction> one = transactions.stream()
				.filter(t -> t.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue))
				.collect(Collectors.toList());
		System.out.println(one);

		// 문제2: 거래자가 근무하는 모든 도시를 중복없이 나열하시오.
		List<String> cities = transactions.stream()
				.map(transaction -> transaction.getTrader().getCity())
				.distinct()
				.collect(Collectors.toList());
		System.out.println(cities);

		// 문제3: 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬
		List<Trader> traders = transactions.stream()
				.map(Transaction::getTrader)
				.filter(trader -> trader.getCity().equals("Cambridge"))
				.distinct()
				.sorted(Comparator.comparing(Trader::getName))
				.collect(Collectors.toList());
		System.out.println(traders);


		// 문제4: 모든 거래자의 이름을 알파벳순으로 정렬해서 반환
		String names = transactions.stream()
				.map(transaction -> transaction.getTrader().getName())
				.distinct()
				.sorted()
				.reduce("", (a, b) -> a + b);
		System.out.println(names);

		// 문제5: 밀라노에 거래자가 있는가?
		boolean number5 = transactions.stream()
				.anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
		System.out.println(number5);

		// 문제6: 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력
		int sum = transactions.stream()
				.filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
				.map(Transaction::getValue)
				.reduce(0, Integer::sum);
		System.out.println(sum);

		transactions.stream()
				.filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
				.map(Transaction::getValue)
				.forEach(System.out::println);

		// 문제7: 전체 트랜잭션 중 최대 값은 얼마인가?
		Optional<Transaction> max = transactions.stream()
				.max(Comparator.comparing(Transaction::getValue));
		System.out.println(max);

		Optional<Integer> a = transactions.stream()
				.map(Transaction::getValue)
				.reduce(Integer::max);
		System.out.println(a);

		// 문제8: 전체 트랜잭션 중 최소 값
		Optional<Integer> min = transactions.stream()
				.map(Transaction::getValue)
				.reduce((t1, t2) -> t1 < t2 ? t1 : t2);
		System.out.println(min);

		// 위와 같은 방식은 박싱 비용이 포함되어 있다. => 기본형 특화 스트림
		int sum1 = transactions.stream()
				.mapToInt(Transaction::getValue)
				.sum();
		System.out.println(sum1);

		// 만약 숫자 스트림을 객체 스트림을 복원
		IntStream intStream = transactions.stream()
				.mapToInt(Transaction::getValue);
		Stream<Integer> stream = intStream.boxed(); // boxed();

		// OptionalInt
		int maxValue = menu.stream()
				.mapToInt(Dish::getCalories)
				.max()
				.orElse(1); // 값이 없을 때 기본 최댓값을 명시적으로 설정
		System.out.println(maxValue);

		// 숫자 범위
		IntStream evenNumbers = IntStream.rangeClosed(1, 100)
				.filter(n -> n % 2 == 0);
		System.out.println(evenNumbers.count());
	}

}
