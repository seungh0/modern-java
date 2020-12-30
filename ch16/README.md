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
      