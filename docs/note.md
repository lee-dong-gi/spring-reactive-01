
## [inflearn - Kevin의 알기 쉬운 Spring Reactive Web Applications: Reactor 1부](https://www.inflearn.com/course/spring-reactive-web-application-reactor1%EB%B6%80/dashboard)

---

### Reactor 용어정의

- Publisher: 발행자, 게시자, 생산자, 방출자(Emitter)
- Subscriber: 구독자, 소비자
- Emit: Publisher가 데이터를 내보내는것(방출하다, 내보내다, 통지하다)
- Sequence: Publisher가 emit하는 데이터의 연속적인 흐름을 저의 해 놓은 것. Operator 체인 형태로 정의
- Subscribe: Subscriber가 Sequence를 구독하는 것
- Dispose: Subscriber가 Sequence를 구독 해지 하는 것
- flatMap: 각 요소에 대해 새로운 Publisher나 Stream을 생성하고, 이를 하나의 스트림으로 평탄화하여 결과를 방출

---

### Mono

- 0개 또는 1개의 데이터를 emit하는 Publisher(Compare with RxJava Maybe)
- 데이터를 emit하는 과정에서 에러가 발생하면 onError signal을 emit한다.
- com.itvillage.section03.class01 패키지 참고

![image](https://github.com/user-attachments/assets/40df2c8b-332b-4e47-8896-c72ce0334cbb)

---

### Flux

- 0개 ~ N개의 데이터를 emit하는 Publisher이다.
- 데이터를 emit하는 과정에서 에러가 발생하면 onError signal을 emit한다.
- com.itvillage.section03.class01 패키지 참고

![image](https://github.com/user-attachments/assets/7f7dfdbd-13c7-4fe5-8bc3-7b1d2dfdba65)

---

### Cold Sequence

- Subscribe 할때마다 타임라인이 새롭게 생성됨
- 이전에 emit된 데이터를 모두 받을 수 있음
- com.itvillage.section04.class01.ColdSequenceExample 클래스 참고

![image](https://github.com/user-attachments/assets/0c06a7cf-4a53-43ec-875d-b1fb069ddc01)

---

### Hot Sequence

- 타임라인이 하나만 생성됨
- Subscribe 한 시점 이후에 emit된 데이터만 받을 수 있음
- com.itvillage.section04.class01.HotSequenceExample 클래스 참고

![image](https://github.com/user-attachments/assets/0bbf4369-d9c1-4e88-8944-6992372ba67e)

---

### Publisher와 Subscriber 간의 프로세스
- subscriber가 publisher를 subscribe() 함
- publisher가 publisher에게 Send onSubscribe signal 구독이 정상적으로 이루어졌음을 전달
- subscriber가 데이터를 전달받기위해 publisher에게 request signal of N을 전달
- publisher는 전달받은 request signal of N에 해당되는 데이터를 전송(Send on Next sigal of N)하면서 데이터를 emit함
- publisher로 부터 전달받은 데이터 emit이 끝나면 subscriber가 다시 request signal of N을 전송하면서 반복
- publisher가 emit할 데이터가 없으면 Send onComplete signal을 subscriber에게 전달
- data를 emit하는 과정에서 error 발생 시 publisher가 publisher에게 on Error signal 전달 

![image](https://github.com/user-attachments/assets/da1b48ef-59ee-4454-a52b-6769a3c817f7)

---

### Backpressure란?
- 들어오는 데이터를 적절하게 제어해서 과부화 발생을 방지하는 기능
- `com.itvillage.section05.class01` 참고

[데이터 처리 병목현상 예시]
![img.png](img.png)

#### 해결방법
- Subscriber가 적절히 처리할 수 있는 수준의 데이터 개수를 Publisher에게 요청
  - Subscriber가 데이터를 처리완료하면 Publisher에게 처리할 데이터를 요청하여 받아오는 방식
- Backpressure 전략을 사용하는 방법
  - IGNORE 전략: Backpressure를 적용하지 않음
  - ERROR 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, Exception을 발생
  - DROP 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 먼저 emit된 데이터부터 Drop 시킴(drop된 데이터는 subscriber에게 전달되지 않음)
    - ![img_2.png](img_2.png)
  - LATEST 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 가장 최근에 emit된(가장 나중에) 데이터부터 버퍼에 채움
    - ![img_3.png](img_3.png)
  - BUFFER 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 안에 있는 데이터를 Drop 시키는 전략
    - DROP LATEST 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 가장 최근에 들어온 데이터를 Drop 시킴
      - ![img_4.png](img_4.png)
    - DROP OLDEST 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 가장 나중에 들어온 데이터를 Drop 시킴
      - ![img_5.png](img_5.png)
---

### Sinks
- Reactive Streams에서 발생하는 signal을 프로그래밍적으로 emit(push)할 수 있는 기능을 가지고 있는 Publisher의 일종
- Thread-Safe하게 signal을 발생시키기 때문에 Processor보다 더 나은 대안이 됨
- Sinks.one은 Mono에 해당되고 Sinks.many는 flux에 해당됨
- Sinks.many().unicast: 하나의 subscriber만 허용
- Sinks.many().multicast: 어려개의 subscriber 허용, 첫번째 구독이 발생하는 시점에 data emit이 시작되는 웜업 방식(hot sequence)으로 동작함
- Sinks.many().replay: subscriber가 구독을 한 시점에 가장 최근에 emit된 데이터를 limit으로 설정된 개수 만큼 전달받음, all로 설정하면 모두 받음
- `com.itvillage.section06.class01` 참고

---

### Scheduler

- Scheduler를 위한 전용 Operator
  - publishOn(): Operator 체인에서 Downstream Operator의 실행을 위한 스레드를 지정
    - `com.itvillage.section07.class01.SchedulerOperatorExample02` publishOn 참고
  - subscribeOn(): 최상위 Upstream Publisher의 실행을 위한 스레드를 지정, 원본 데이터 소스를 emit하기 위한 스케줄러를 지정
    - `com.itvillage.section07.class01.SchedulerOperatorExample04` fromArray 참고
  - parallel(): Downstream에 대한 데이터 처리를 병렬로 분할 처리하기 위한 스레드 지정, runOn()을 사용하지 않으면 병렬 처리가 수행되지 않음
    - ![img_6.png](img_6.png)
    - `com.itvillage.section07.class00` 참고
- publishOn() 동작 이해
  - publishOn()을 지정하기 이전에는 main 스레드에서 작동하고 publishOn()을 지정한 이후에는 새롭게 지정된 스레드가 사용됨
  - ![img_7.png](img_7.png)
  - ![img_8.png](img_8.png)
- subscribeOn() 동작 이해
  - subscribeOn()은 publishOn()의 위치에 상관 없이 publishOn()을 만나기 이전의 Upstream Operator 스레드를 지정함, publishOn()이 없다면 subscribeOn()이 지정한 스레드로 모두 실행됨
  - ![img_9.png](img_9.png)
  - ![img_10.png](img_10.png)
  - ![img_11.png](img_11.png)
- Scheduler의 종류
  - Schedulers.immediate(): 별도의 스레드를 추가할당하지 않고 현재 스레드에서 실행
  - Schedulers.single(): 
    - 하나의 스레드를 재사용함
    - 저지연(low latency) 일회성 실행에 최적화
  - Schedulers.boundedElastic(): 
    - 스레드 풀(ExecutorService 기반)을 생성하여 생성된 스레드를 재사용
    - 생성할 수 있는 스레드 수에 제한이 있음(max(default): number of cpu core * 10)
    - 긴 실행 시간을 가질 수 있는 Blocking I/O 작업에 최적화
    - 더이상 사용할 스레드도 없고 대기할 Queue도 없을 경우 Exception 발생
    - com.itvillage.section07.class02.SchedulersNewBoundedElasticExample01 참고
      - ![img_12.png](img_12.png)
  - Schedulers.parallel(): 
    - 여러개의 스레드를 할당해서 동시작업 수행
    - Non-Blocking I/O 작업에 최적와
    - CPU CORE 수만큼의 스레드를 생성함
    - `com.itvillage.section07.class02.SchedulersNewParallelExample01` 참고
  - Schedulers.fromExecutorService()
    - 기존에 사용하고 있는 ExecutorService가 있다면 해당 서비스를 사용하여 스레드 생성(권장x)
    - 의미있는 식별자를 제공하기 때문에 Metric에서 주로 사용됨
  - Schedulers.newXXXX():
    - 다양한 유형의 새로운 Scheduler를 생성 할 수 있다.
    - newSingle(), newParallel(), newboudedElastic()
    - Scheduler의 이름 지정 가능
    - scheduler.dispose(): new로 생성된 경우 일정시간동안 종료되지 않음 그래서 해당 구문으로 작업이 끝난 후 즉시 종료 할 수 있음

---

### Context
- `com.itvillage.section08` 참고
- Reactor Sequence 상에서 상태를 저장하고 저장된 상태 값을 Operator 체인에서 공유해서 사용할 수 있는 인터페이스
- 스레드가 달라도 공유가능
- key, value 형태로 저장되고 contextWrite()을 사용함
- 값을 읽어오기 위해서는 읽기전용 ContextView를 사용
  - deferContextual()
  - transformDeferredContextual()
- Context API
  - put(key, value): key/value 형태로 Context에 값을 씀
  - Context.of(key1, value1, key2, value2,...): key/value 형태로 Context에 여러개의 값을 씀
  - putAll(ContextView): 파라미터로 입력된 ContextView를 merge
  - delete(key): Context에서 key에 해당하는 value를 삭제
- Context View API
  - get(key): ContextView에서 key에 해당하는 value를 반환한다.
  - getOrEmpty(key): ContextView에서 key에 해당하는 value를 Optional로 래핑해서 반환
  - getOrDefault(key, default value): ContextView에서 key에 해당하는 value를 가져옴, key에 해당하는 value가 없으면 default value를 가져옴
  - hasKey(key): ContextView에서 특정 key가 존재하는지 확인
  - isEmpty(): Context가 비어있는지 확인
  - size(): Context내에 있는 key/value 쌍의 개수를 반환
- Context의 특징
  - `com.itvillage.section08.class02` 참고
  - Context는 각각의 Subscriber를 통해 Reactor Sequence에 연결되며 체인에서 각각의 Operator들이 실행 스레드가 달라도 연결된 Context에 접근할 수 있음
    - `ContextFetureExample01` 참고
    - Subscriber 별로 다른 Context를 가짐
  - Context는 Operator 체인의 맨 아래에서부터 위로 전파됨
    - `ContextFetureExample02` 참고
    - Context는 Downstream에서 Upstream으로 전파됨
    - Operator 체인에서 Context read메서드가 Context write 메서드 밑에 있을 경우에는 write된 값을 read 할 수 없음
    - 따라서 일반적으로 Context에 write할때에는 Operator 체인의 마지막에 둠
  - 동일한 key에 대해서는 값을 덮어씀(이것 역시 밑에서 위로)
    - `ContextFetureExample03` 참고
  - 메인 Operator 내부에서 Sequence를 생성하는 flatMap() 같은 Operator내에서 write된 Context의 값은 Inner Sequence 내부에서만 유효하고, 외부 Operator 체인에서는 보이지 않음
    - `ContextFetureExample04` 참고
    - Inner Sequence에서 저장한 Context는 메인 Sequence에서 보이지 않음
    - 메인 Sequence에서 저장한 Context는 Inner Sequence에서 보임

---

### Debugging
- Debugging 관련 용어
  - stacktrace: 호출된 메서드에 대한 Stack Frame에 대한 리포트
  - assembly: 새로운 Flux가 선언된 지점
  - traceback: 실패한 operator의 stacktrace를 캡쳐한 정보
- Debugging 방법
  - 전역 Debug 모드 활성화
    - `com.itvillage.section09.class00` 참고
    - Debug 모드 시, Operator의 stacktrace capturing을 통해 디버깅에 필요한 정보를 측정 
    - Hooks.onOperatorDebug()를 통해서 Debug 모드를 활성화 할 수 있음
    - Hooks.onOperatorDebug()는 Operator 체인이 선언되기 전에 수행되어야 함
    - Debug 모드를 활성화 하면 Operator 체인에서 에러 발생 시, 에러가 발생한 Operator의 위치를 알려줌
    - 사용이 쉽지만 애플리케이션 내 모든 Operator의 assembly(New Flux or Mono)를 캡처하기 때문에 비용이 많이 듬
  - checkpoint() Operator를 사용(Locally)
    - `com.itvillage.section09.class01` 참고
    - 특정 Operator 체인 내에서만 assembly stacktrace를 캡쳐
    - checkpoint(description)를 사용하면 에러 발생 시, chpeckpoint(description)를 추가한 지점의 assembly stacktrace를 생략하고 description을 통해 에러 발생 지점을 예상할 수 있음
    - checkpoint(description, true) = checkpoint( ) + checkpoint(“description”)
      - 에러 발생 시, 고유한 식별자 등의 description과  assembly stack trace(traceback)를 모두 출력한다.
    - 강의 듣다보니 밑에서 하나하나씩 추가하면서 지정하는게 좋을듯
  - log() operator를 사용해서 Reactor Sequence에서 발생하는 Signal을 확인하는 방법
    - `com.itvillage.section09.class02` 참고
    - Flux 또는 Mono에서 발생하는 signal event를 출력해준다(onNext, onError, onComplete, subscriptions, cancellations, requests)
    - 여러개의 log()를 사용할 수 있으며, Operator 마다 전파되는 singal event를 확인할 수 있다.
    - Custom Category를 입력해서 Operator 마다 출력되는 signal event를 구분할 수 있다.
    - 에러 발생 시, stacktrace도 출력해준다.
    - debug mode 라면 traceback도 출력해준다.

---

### Testing
- StepVerifier를 이용한 Testing
  - `section10.class01` 참고
  - Flux 또는 Mono로 선언된 Operator 체인을 구독 시, 동작 방식을 테스트하기 위한 가장 일반적인 테스트 방식
  - expectXXXX()를 이용해서 Sequence 상에서 예상되는 Signal의 기대값을 assertion할 수 있다.
  - verify() Operator를 호출함으로써 전체 Operator 체인의 테스트를 트리거 한다.
  - verifyXXXX()를 이용해서 전체 Operator 체인의 테스트를 트리거 + 종료 또는 에러 이벤트 검증을 수행할 수 있다.
  - 실제 수행되는 시간과 관련된 테스트를 수행할 수 있다.
  - Backpressure 테스트
    - hasDropped(), hasDiscarded() 등을 이용해서 backpressure 테스트를 수행할 수 있다.
  - Context 테스트
    - expectAccessibleContext()를 이용해서 접근 가능한 Context가 있는지 테스트 할 수 있다.
    - hasKey()를 사용하여 Context의 key가 존재하는지 검증할 수 있다.
  - 기록된 데이터를 이용한 테스트
    - recordWith()를 사용하여 emit 된 데이터를 기록할 수 있다.
    - consumeRecordedWith()를 사용하여 기록된 데이터들을 소비하며 검증할 수 있다.
    - expectRecordedMatches()를 사용하여 기록된 데이터의 컬렉션을 검증할 수 있다.
- TestPublisher를 이용한 Testing
  - Testing 목적에 사용하기 위한 Publisher이다.
  - 개발자가 직접 프로그래밍을 통해 signal을 발생시킬 수 있다.
  - 주로 특정한 상황을 재현하여 테스트하고 싶은 경우 사용할 수 있다.
  - 리액티브 스트림즈 사양을 준수하는지의 여부를 테스트할 수 있다.
- PublisherProbe를 이용한 Testing
  - Operator 체인의 실행 경로를 검증할 수 있다.
  - 주로 조건에 따른 분기로 인해서 Sequence가 분기 되는 경우, 실행 경로를 추적해서 정상적으로 실행이 되었는지 확인할 수 있다.
  - 해당 실행 경로대로 정상적으로 실행되었는지의 여부는 assertWasSubscribed(),  assertWasRequested(), assertWasCancelled() 를 통해 검증할 수 있다.    
