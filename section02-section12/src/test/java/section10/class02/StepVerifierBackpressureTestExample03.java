package section10.class02;

import com.itvillage.section10.class01.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure DROP 전략을 검증하는 예제
 *  - expectError()를 사용하여 에러가 발생되었는지 검증
 *  - verifyThenAssertThat()을 사용하여 검증 이후에 assertion method 를 사용하여 추가 검증을 할 수 있다.
 *  - hasDiscardedElements()를 사용하여 discard된 데이터가 있는지를 검증한다. Backpressure DROP 전략은 Drop된 데이터가 discard된다.
 *  - hasDiscarded()를 사용하여 discard된 데이터가 무엇인지 검증한다. Backpressure DROP 전략은 Drop된 데이터가 discard된다.
 */
public class StepVerifierBackpressureTestExample03 {
    @Test
    public void generateNumberTest() {
        StepVerifier
                .create(BackpressureExample.generateNumberByDropStrategy(), 1L)
                .thenConsumeWhile(num -> num >= 1)
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscardedElements() // DROP 전략
                .hasDiscarded(2, 3, 4, 5, 6, 98, 99, 100); // DROP 전략
//                .hasDropped(2, 3, 4, 5, 6, 98, 99, 100); // 백프레셔 전략 중 DROP 전략을 의미하는 것이 아님, 그냥 어떠한 처리없이 날라간 데이터를 의미
    }
}
