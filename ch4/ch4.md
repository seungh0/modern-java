# 스트림

- 선언형으로 컬렉션 데이터를 처리할 수 있다.
- 멀티스레드 코드를 구현하지 않아도 데이터를 투명하게 병렬로 처리 가능.
- filter, sorted, map, collect 같은 여러 빌딩 블록 연산을 연결해서 복잡한 데이터 처리 파이프라인을 만들 수 있다.

### 스트림이란?

> 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소.

1. 연속된 요소
- 컬렉션과 마찬가지로 스트림은 특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스를 제공
- 컬렉션은 자료구조이므로 컬렉션에서는 ArrayList를 사용할 것인지 LinkedList를 사용할 것인지에 대한 시공간의 복잡성과 관련된 요소 저장 및 접근 연산이 주를 이룬다.

- 반면 스트림은 filter, sorted, map처럼 표현 계산식이 주를 이룬다.
- 즉 컬렉션의 주제는 데이터이고, 스트림의 주제는 계산이다.

2. 소스
- 스트림은 컬렉션, 배열, I/O 자원 등의 데이터 제공 소스로부터 데이터를 소비한다. 정렬된 컬렉션으로 스트림을 생성하면
정렬이 그대로 유지된다. 즉 리스트로 스트림을 만들면, 스트림의 요소는 리스트의 요소와 같은 순서를 유지한다.
  
3. 데이터 처리 연산

### 스트림의 중요 특징
1. 파이프라이닝
    - 대부분의 스트림 연산은 스트림 연산끼리 연결해서 커다란 파이프라인을 구성할 수 있도록 스트림 자신을 반환.
    - 그 덕분에 게으름, 쇼트서킷 같은 최적화도 얻을 수 있다.

2. 내부 반복
    - 컬렉션 -> 반복자를 이용해 명시적으로 반복하는 외부 반복과 다르게
    - 스트림 -> 내부 반복을 지원

### 스트림
- 스트림을 이론적으로 요청할 때만 요소를 계산하는 자료구조. (스트림에 요소를 추가하거나 요소를 제거할 수 없음)
- 딱 한번만 탐색할 수 있다.
- 외부 반복(컬렉션)과 내부 반복(스트림)
- 내부 반복으로써 얻게되는 이점은, 작업을 투명하게 병렬로 처리하거나 더 최적화 된 다양한 순서로 처리할 수 있다.
- 또한 스트림 라이브러리 내부 반복은 데이터 표현과 하드웨어를 활용한 병렬성 구현을 자동으로 선택.
- but 외부 반복에서는 병렬성을 스스로 관리해야함(synchronized ...)

### 스트림 연산
1. 중간 연산
   - 연결할 수 있는 스트림 연산
    - 단말 연산을 스트림 파이프라인에 실행하기 전까지 아무 연산도 수행하지 않음. (게으르다 lazy)
    - 중간 연산을 합친 다음에 합쳐진 중간연산을 최종 연산으로 한번에 처리하기 떄문
    - 이러한 lazy 특성으로, 몇가지 최적화 효과를 얻을 수 있다.
    - limit 연산, 쇼토서킷 이라고 불리는 기법
    - 루프 퓨전
    - filter, map, limit, sorted, distinct
2. 최종 연산
    - 스트림을 닫는 연산
    - 보통 최종 연산에 의해, List, Integer, void 등 스트림 이외의 결과가 반환.
    - forEach, count, collect
    
### 스트림의 이용 과정
- 질의를 수행할 컬렉션과 같은 데이터 소스
- 스트림 파이프라인을 구성할 중간 연산 연결
- 스트림 파이프라인을 실행하고 결과를 만들 최종 연산