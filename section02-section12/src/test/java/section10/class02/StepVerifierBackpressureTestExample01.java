package section10.class02;

import com.itvillage.section10.class01.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure 전략에 따라 Exception이 발생하는 예제
 *  - request 데이터 개수보다 많은 데이터가 emit 되어 OverFlowException이 발생
 *  - OverFlowException이 발생하게 된 데이터는 discard 된다.
 *  - 나머지 emit 된 데이터들은 Hooks.onNextDropped()에 의해 drop된다.
 */
public class StepVerifierBackpressureTestExample01 {
    @Test
    public void generateNumberTest() {
        StepVerifier
                // upstream에 데이터를 요청하는 개수, 1개씩 데이터 요청하도록, 그러나 generateNumberByErrorStrategy함수는 100개의 데이터를 emit하게 되어서 에러가 발생
                .create(BackpressureExample.generateNumberByErrorStrategy(), 1L)
                .thenConsumeWhile(num -> num >= 1) // emit 된 데이터들을 소비한다.
                .verifyComplete();
    }
}
