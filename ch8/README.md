# 8. 컬렉션 API 개선

## 컬렉션 팩토리

### 기존의 리스트를 만드는 방법

```java
        List<String> friends=new ArrayList<>();
		friends.add("Raphael");
		friends.add("Seungho"); // 많은 코드가 필요한 단점

		List<String> friends2=Arrays.asList("Raphael","Seungho"); // 고정 크기의 리스트
		friends2.set(0,"Richard"); // 요소를 갱신하는 것은 가능함.
//		friends2.add("Ti"); // 그러나 새 요소를 추가하거나 요소를 삭제하는 것은 불가능함. (UnsupportedOpertaionException)

		Set<String> friendSets=new HashSet<>(Arrays.asList("Raphel","seungho"));
		Set<String> friendSets2=Stream.of("Raphael","Olivia","tha")
		.collect(Collectors.toSet());
```

### 리스트 팩토리

```java
        List<String> friends=List.of("Raphael","Olivia");
//		friends.add("Chin-chun"); (UnsupportedOpertaionException)
//		friends.set(0, "ABC"); // UnsupportedOpertionException
```

- 변경할 수 없는 리스트가 만들어졌기 떄문임

- 컬렉션이 의도치 않게 변하는 것을 막을 수 있음)

- 리스트를 바꿔야 하는 상황이면 직접 리스트를 만들면 된다.

## 오버로딩 vs 가변인수

```java
static<E> List<E> of(E...elements)
```

위와 같이 가변 인수를 통해 다중 요소를 받을 수 있도록 자바 API를 만들지 않고

```java
static<E> List<E> of(E e1,E e2,E e3)
static<E> List<E> of(E e1,E e2,E e3,E e4)
```

위와 같이 10개의 오버로딩을 사용할까? (10개 이상의 경우 가변인수를 사용함)

- 내부적으로 가변 인수 버전은 추가 배열을 할당해서 리스트로 감싼다. 따라서 배열을 할당하고 초기화하며 나중에 가비지 컬렉션을 하는 비용을 지불해야 한다.
- 고정된 숫자의 요소 (최대 열개)를 API로 정의하므로 이런 비용을 제거할 수 있다.

### 집합 팩토리

```java
        Set<String> friends=Set.of("Raphael","Olivia");
		Set<String> friends2=Set.of("Raphael","Olivia","Olivia"); // IllegalArgumentException - 중복 요소
```

### 맵 팩토리

```java
        // 10개 이하의 키와 쌍을 가진 작은 맵을 만들때는 아래의 메소드가 유용
		Map<String, Integer> ageOfFriends=Map.of("Raphael",30,"Olivia",25);
		System.out.println(ageOfFriends);

		// 그 이상의 맵에서는 Map.entry<K,V> 객체를 인수로 받으며 가변 인수로 구현된 Map.ofEntries 팩토리 메소드를 이용하는 것이 좋다.
		Map<String, Integer> ageOfFriends2=Map.ofEntries(
		entry("Raphael",30),
		entry("Olivia",25));
```

## 리스트와 집합 처리

자바 8에서는 List, Set 인터페이스에 다음과 같은 메소르를 추가 하였다.

- removeIf: 프레디케이트를 만족하는 요소를 제거
- replaceAll: 리스트에서 이용할 수 있는 기능으로 UnaryOperator 함수를 이용해 요소를 바꾼다.
- sort: List 인터페이스에서 제공하는 기능으로 리스트를 정렬한다.

이들 메소드는 호출한 컬렉션 자체를 바꾼다. 새로운 결과를 만드는 스트림 동작과 달리 이들 메소드는 기존 컬렉션을 바꾼다.

컬렉션을 바꾸는 동작은 에러를 유발하며 복잡함을 더하기 때문에 자바 8에서 다음과 같은 메소드를 추가함.

