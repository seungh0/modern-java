package me.ch8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class CollectionFactory {

	public static void main(String[] args) {
		disadvantage();
		factoryList();
		factorySet();
		factoryMap();
	}

	private static void disadvantage() {
		List<String> friends = new ArrayList<>();
		friends.add("Raphael");
		friends.add("Seungho"); // 많은 코드가 필요한 단점

		List<String> friends2 = Arrays.asList("Raphael", "Seungho"); // 고정 크기의 리스트
		friends2.set(0, "Richard"); // 요소를 갱신하는 것은 가능함.
//		friends2.add("Ti"); // 그러나 새 요소를 추가하거나 요소를 삭제하는 것은 불가능함. (UnsupportedOpertaionException)

		Set<String> friendSets = new HashSet<>(Arrays.asList("Raphel", "seungho"));
		Set<String> friendSets2 = Stream.of("Raphael", "Olivia", "tha")
				.collect(Collectors.toSet());
	}

	/**
	 * 자바9: 팩토리 메소드
	 */
	private static void factoryList() {
		List<String> friends = List.of("Raphael", "Olivia");
		System.out.println(friends);
//		friends.add("Chin-chun"); (UnsupportedOpertaionException)
//		friends.set(0, "ABC"); // UnsupportedOpertionException
	}

	private static void factorySet() {
		Set<String> friends = Set.of("Raphael", "Olivia");
//		Set<String> friends2 = Set.of("Raphael", "Olivia", "Olivia"); // IllegalArgumentException - 중복 요소
	}

	private static void factoryMap() {
		// 10개 이하의 키와 쌍을 가진 작은 맵을 만들때는 아래의 메소드가 유용
		Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25);
		System.out.println(ageOfFriends);

		// 그 이상의 맵에서는 Map.entry<K,V> 객체를 인수로 받으며 가변 인수로 구현된 Map.ofEntries 팩토리 메소드를 이용하는 것이 좋다.
		Map<String, Integer> ageOfFriends2 = Map.ofEntries(
				entry("Raphael", 30),
				entry("Olivia", 25));
	}

}
