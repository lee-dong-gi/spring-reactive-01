package section10.class03;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * TestPublisher 를 사용해서 서비스 로직의 메서드에 대한 Unit Test 를 실시하는 예제
 *  - 정상 동작하는 TestPublisher
 *  - emit() 사용
 */
public class TestPublisherTestExample03 {
    @Test
    public void divideByTwoTest() {
        TestPublisher<Integer> source = TestPublisher.create();

        StepVerifier
                .create(source.flux().log())
                .expectSubscription()
                .then(() -> source.emit(1, 2, 3)) // emit은 onComplete 시그널을 전송시켜주기 때문에 정상 종료됨
                //.then(() -> source.next(1, 2, 3)) // next는 onComplete 시그널을 전송 안하기 때문에 무한 대기
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectComplete()
                .verify();
    }
}
