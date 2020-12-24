package me.ch8;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Map.entry;

public class ListMethod {

	public static void main(String[] args) {
		listAndSetMethod();
		mapMethod();
		calculatePattern();
		calculatePattern2();
		mapReplace();
		mapMerge();
		mapMerge2();
		example1();
		example2();
		concurrentHashMap();
	}

	private static void listAndSetMethod() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		System.out.println("numbers: " + numbers.toString());

// 		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9); // 변경 할수 없음

		numbers.removeIf(n -> (n % 3 == 0));
		System.out.println("numbers(after remove): " + numbers.toString());

		numbers.replaceAll(n -> n + 1);
		System.out.println(numbers.toString());
	}

	private static void mapMethod() {
		Map<String, Integer> ageOfFriends = Map.of("Seungho", 23, "AAA", 22);

		// 귀찮다.
		for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
			String friend = entry.getKey();
			Integer age = entry.getValue();
			System.out.println(friend + age);
		}

		// 자바 8 에서부터 Map 인터페이스는 BiConsumer를 인수로 받는 forEach 메소드를 지원하므로 코드를 조금 더 간단하게 구현할 수 있다.
		ageOfFriends.forEach((friend, age) -> System.out.println(friend + age));

		//Entry.comparingByValue, Entry.comparingByKey
		ageOfFriends.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.forEachOrdered(System.out::println);

		// getOrDefault
		int result = ageOfFriends.getOrDefault("AAA", 0); // 22
		System.out.println(result);

		int result2 = ageOfFriends.getOrDefault("BBB", 0); // 0
		System.out.println(result2);
	}

	// 계산 패턴
	// computeIfAbsent : 제공된 키에 해당하는 값이 없으면 키를 이용해 새 값을 계산하고 멤에 추가
	// computeIfPresent: 제공된 키가 존재하면 새 값을 계산하고 맵에 추가
	// compute: 제공된 키로 새 값을 계싼하고 맵에 저장
	private static void calculatePattern() {
		String friend = "Raphael";
		Map<String, List<String>> friendsToMovies = new HashMap<>();
		friendsToMovies.put("Seungho", Arrays.asList("123", "234"));
		List<String> movies = friendsToMovies.get(friend);
		if (movies == null) {
			movies = new ArrayList<>();
			friendsToMovies.put(friend, movies);
		}
		movies.add("Star wars");
		System.out.println(friendsToMovies);
	}
	// 귀찮은 작업

	private static void calculatePattern2() {
		String friend = "Raphael";
		Map<String, List<String>> friendsToMovies = new HashMap<>();
		friendsToMovies.put("Seungho", Arrays.asList("123", "234"));

		friendsToMovies.computeIfAbsent(friend, name -> new ArrayList<>()).add("Star wars");
		System.out.println(friendsToMovies);

		friendsToMovies.remove("Seungho", Arrays.asList("123", "234"));
		System.out.println(friendsToMovies);
	}

	private static void mapReplace() {
		Map<String, String> favoriteMovies = new HashMap<>();
		favoriteMovies.put("Raphael", "Star wars");
		favoriteMovies.put("Olivia", "james bond");
		System.out.println(favoriteMovies);

		favoriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
		System.out.println(favoriteMovies);
	}

	private static void mapMerge() {
		Map<String, String> family = Map.ofEntries(
				entry("Teo", "Star wars"),
				entry("Cristina", "James bond"));
		Map<String, String> friends = Map.ofEntries(
				entry("Raphael", "Star wars"));
		Map<String, String> everyone = new HashMap<>(family);
		everyone.putAll(friends);
		System.out.println(everyone);
		// 중복된 키가 없다면 위 코드는 잘 동작한다.
	}

	private static void mapMerge2() {
		Map<String, String> family = Map.ofEntries(
				entry("Teo", "Star wars"),
				entry("Cristina", "James bond"));
		Map<String, String> friends = Map.ofEntries(
				entry("Teo", "Star wars"));
		Map<String, String> everyone = new HashMap<>(family);
		friends.forEach((key, value) -> {
			everyone.merge(key, value, (movie1, movie2) -> movie1 + " & " + movie2); // 키가 중복되는 경우 대응
		});
		System.out.println(everyone);

		Map<String, Long> moviesCount = Map.ofEntries(
				entry("Teo", 10L),
				entry("Cristina", 20L));
		Map<String, Long> moviesToCount = new HashMap<>(moviesCount);
		String movieName = "JamesBond";
		Long count = moviesToCount.get(movieName);
		if (count == null) {
			moviesToCount.put(movieName, 1L);
		} else {
			moviesToCount.put(movieName, count + 1);
		}
		System.out.println(moviesToCount);
		// 위 코드를

		String movieName2 = "Melong";
		moviesToCount.merge(movieName2, 1L, (key, cnt) -> cnt + 1L);
		System.out.println(moviesToCount);
	}

	// 위 코드를 바꾸시용
	private static void example1() {
		Map<String, Integer> movies = new HashMap<>();
		movies.put("JamesBond", 20);
		movies.put("Matix", 15);
		movies.put("Harry", 5);
		Iterator<Map.Entry<String, Integer>> iterator = movies.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			if (entry.getValue() < 10) {
				iterator.remove();
			}
		}
	}

	private static void example2() {
		Map<String, Integer> movies = new HashMap<>();
		movies.put("JamesBond", 20);
		movies.put("Matix", 15);
		movies.put("Harry", 5);

		movies.entrySet().removeIf((entry) -> entry.getValue() < 10);
		System.out.println(movies);
	}

	private static void concurrentHashMap() {
		ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
		map.put("123", 20L);
		map.put("234", 30L);

		long parallelismThreshold = 1;
		Optional<Long> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
		System.out.println(maxValue);
	}

}
