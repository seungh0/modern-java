# 7. 병렬 데이터 처리와 성능

## 병렬 스트림

- 컬렉션에 parallelStraem을 호출하면 병렬 스트림이 생성.
- 병렬 스트림이란 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림

### 병렬 스트림에서 사용하는 스레드 풀 설정

- 병렬 스트림은 내부적으로 ForkJoinPool을 사용한다.
- 기본적으로 ForkJoinPool은 프로세서 수에 상응하는 스레드를 갖는다.

### 병렬 스트림 주의사항

- 병렬화를 이용하면 스트림을 재귀적으로 분할해야 하고, 각 서브스트림을 서로 다른 스레드의 리듀싱 연산으로 할당하고, 이들 결과를 하나의 값으로 합쳐야 한다. 멀티 코어 간의 데이터 이동은 생각보다 비쌈.
- 병렬 스트림과 병렬 계산에서는 공유된 가변 상태를 피해야 한다.

```java
    public static void main(String[]args){
		long result=sideEffectSum(10_000_000L); // 올바른 값이 나오지 않음 (50000005000000X)
		System.out.println(result);
		}

private static long sideEffectSum(long n){
		Accumulator accumulator=new Accumulator();
		LongStream.rangeClosed(1,n)
		.parallel()
		.forEach(accumulator::add); // 여러 스레드에서 동시에 누적자, (total += value)를 실행하면서 이러한 문제가 발생.
		return accumulator.total;
		}

private static class Accumulator {
	public long total = 0; // total에 접근할 때 마다 (다수의 스레드에서 동시에 데이터에 접근하는) 데이터 레이스 문제가 발생한다.

	public void add(long value) {
		total += value;
	}
}
```

### 병렬 스트림 효과적으로 사용하기

- 순차 스트림과 병렬 스트림 중 어떤 것이 좋을지 모르겠다면 적절한 벤치마크로 직접 성능을 측정하는 것이 바람직하다.
- 박싱을 주의하라. 자동 방식과 언박싱은 성능을 크게 저하시킬 수 있는 요소. (기본형 특화 스트림을 사용하는 것이 좋음: IntStream...)
- 순차 스트림보다 병렬 스트림에서 성능이 떨어지는 연산이 있다. 특히 limit나 findFirst처럼 요소의 순서에 의존하는 연산을 병렬 스트림에서 수행하려면 비싼 비용을 치러야 한다.
- 스트림에서 수행하는 전체 파이프라인 연산 비용을 고려하라
- 소량이 데이터에서는 병렬 스트림이 도움 되지 않는다.
- 스트림을 구성하는 자료구조가 적절한지 확인하라.
- 스트림의 특성과 파이프라인의 중간 연산이 스트림의 특성을 어떻게 바꾸는지에 따라 분해 과정의 성능이 달라질 수 있다.
- 최종 연산의 병합 과정 비용을 살펴보라. 병합 과정의 비용이 비싸다면 병렬 스트림으로 얻은 성능의 이익이 서브스트림의 부분 결과를 합치는 과정에서 상쇄될 수 있다.

## 포크/조인 프레임워크