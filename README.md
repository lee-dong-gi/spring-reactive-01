
## inflearn - Kevin의 알기 쉬운 Spring Reactive Web Applications: Reactor 1부

---

### Reactor 용어정의

- Publisher: 발행자, 게시자, 생산자, 방출자(Emitter)
- Subscriber: 구독자, 소비자
- Emit: Publisher가 데이터를 내보내는것(방출하다, 내보내다, 통지하다)
- Sequence: Publisher가 emit하는 데이터의 연속적인 흐름을 저의 해 놓은 것. Operator 체인 형태로 정의
- Subscribe: Subscriber가 Sequence를 구독하는 것
- Dispose: Subscriber가 Sequence를 구독 해지 하는 것

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