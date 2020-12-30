# CompletableFuture: 안정적 비동기 프로그래밍

### Future의 단순 활용
- Future은 계산이 끝났을 때 결과에 접근할 수 있는 참조를 제공한다.
- 시간이 걸릴 수 있는 작업을 Future 내부로 설정하면 호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업을 수행할 수 있다.
- Future의 get메소드로 결과를 가져올 수 있다. get 메소드를 호출했을 때 이미 계산이 완료되어 결과가 준비되었다면 즉시 결과를 반환하지만, 결과가 준비되지
않았다면 작업이 완료될때까지 우리 스레드를 블록시킨다. => 오래 걸리는 작업이 영원히 끝나지 않으면 작업이 끝나지 않을 수 도 있음(스레드가 대기할 최대 타임아웃 시간 설정해야함)

### Future 제한
- Future 인터페이스가 비동기 계산이 끝났는지 확인할 수 있는 isDone(), 계산이 끝나길 기다리는 메소드, 결과 회수 메소드 등을 제공.
- 하지만 이들 메소드만으로는 간결한 동시 실행 코드를 구현하기에 충분하지 않다. (ex) 오래 걸리는 A라는 계산이 끝나면 그 결과를 다른 오개 걸리는 계산B로 전달하시오. 그리고 B의 결과가 나오면 다른 질의의 결과와 B의 결과를 조합하시오) => Future로 구현 hard

- 다음과 같은 선언형 기능이 있다면 유용할 것
    - 두 개의 비동기 계산 결과를 하나로 합친다. 두가지 계산 결과는 서로 독립적일 수도, 의존하는 상황일 수 도 있음.
    - Future 집합이 실행하는 모든 태스크의 완료를 기다린다.
    - Future 집합에서 가장 빨리 완료되는 태스크를 기다렸다가 결과를 얻는다.
    - 프로그램적으로 Future를 완료시킨다.
    - Future 완료 동작에 반응한다.
    => CompletableFuture 클래스 (Future 인터페이스를 구현한 클래스) - 자바 8에서 제공
      
### 동기API vs 비동기API
- 동기 API에서는 메소드를 호출한 다음에 메서드가 계산을 완료할 때까지 기다렸다가 메소드가 반환되면 호출자는 반환된 값으로 계속 다른 동작을 수행.
- 호출자와 피호출자가 각각 다른 스레드에서 실행되는 상황이었더라도 호출자는 피호출자의 동작 완료를 기다렸을 것. 이처럼 동기 API를 사용하는 상황을 블록 호출이라고 한다.

- 비동기 API에서는 메소드가 즉시 반환되며 끝내지 못한 나머지 작업을 호출자 스레드와 동기적으로 실행될 수 있도록 다른 스레드에 할당한다. 이와 같은 비동기 API를 사용하는 상황을
비블록 호출이라고 한다. 다른 스레드에 할당된 나머지 계산 결과는 콜백 메소드를 호출해서 전달하거나 호출자가 계산 결과가 끝날 때 까지 기다림 메소드를 추가로 호출하면서 전달된다.
  주로 I/O 시스템 프로그래밍에서 이와 같은 방식으로 동작을 수행한다.
  
### 비동기 API
```java
	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>(); // 계산 결과를 포함할 CompletableFuture을 생성한다.
		new Thread(() -> {
			try {
				double price = calculatePrice(product); // 다른 스레드에서 비동기적으로 계산을 수행한다.
				futurePrice.complete(price); // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정한다.
			} catch (Exception e) {
				futurePrice.completeExceptionally(e); // 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future을 종료.
			}
		}).start();
		return futurePrice; // 계산 결과가 완료되길 기다리지 않고 Future을 반환한다.
	}
```

### 팩토리 메소드 supplyAsync로 CompletableFuture 만들기
```java
	public Future<Double> getPriceAsyncFactory(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product)); // 팩토리 메소드
	}
```

### 비볼록 코드 만들기
```java
	public List<String> findPricesFuture(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
				.collect(Collectors.toList());
		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
		// 두 map 연산을 하나의 스트림 처리 파이프라인으로 처리하지 않고 두 개의 스트림 파이프라인으로 처리 => 스트림 연산은 게으른 특성이 있으므로 하나의 파이프라인으로 연산을 처리했다면 모든 가격 정보 요청 동작이 동기적, 순차적으로 이루어짐.
	}
```