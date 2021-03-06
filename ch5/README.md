### 쇼트서킷 평가

- 때로는 전체 스트림을 처리하지 않았어라도 결과를 반환할 수 있다.
- 예를 들어 여러 and 연산으로 연결된 커다란 불리언 표현을 평가할때, 하나라도 거짓이라는 결과가 나오면 나머지 표현식의 결과아 상관없이 전체 결과도 거짓이 된다. => 이러한 상황을 쇼트서킷이라고 부른다.

### 정리

- 스트림 API를 이용하면 복잡한 데이터 처리 질의를 표현할 수 있따.
- filter, distinct, takeWhile(JAVA9), dropWhile(Java9), skip, limit 메소드로 스트림을 필터링하거나 자를 수 있다.
- 소스가 정렬되어 있다는 사실을 알 고 있을때 takeWhile dropWhile 메소드를 효과적으로 사용할 수 있다.
- map, flatMap 메소드로 스트림의 요소를 추출하거나 변환할 수 있다.
- findFirst, findAny 메소드로 스트림의 요소를 검색할 수 있다. allMatch, anyMatch, noneMatch 메소드를 이용해서 주어진 프레디케이트와 일치하는 요소를 스트림에서 검색할 수
  있다.

- 이들 메소드는 쇼토서킷, 즉 결과를 찾는 즉시 반환하며, 전체 스트림을 처리하지는 않는다.
- reduce 메소드로 스트림의 모든 요소를 반복 조합하며 값을 도출할 수 있다. 예를 들어 reduce로 스트림의 최댓값이나 모든 요소의 합계를 계산할 수 있다.

- filter, map 등은 상태를 저장하지 않는 상태 없는 연산이다. reduce 같은 연산은 값을 계산하는데 필요한 상태를 저장한다.
- sorted, distinct 등의 메소드는 새로운 스트림을 반환하기에 앞서 스트릠의 모든 요소를 버퍼에 저장해야 한다. 이런 메소드는 상태 있는 연산이라고 한다.

- IntStream, DoubleStream, LongStream은 기본형 특화 스트림이다.
- 컬렉션 뿐만 아니라 값, 배열, 파일, iterate와 generated 같은 메소드로 스트림을 만들 수 있다.
- 무한한 개수의 요소를 가진 스트림을 무한 스트림이라고 한다.