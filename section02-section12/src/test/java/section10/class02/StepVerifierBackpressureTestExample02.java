package section10.class02;

import com.itvillage.section10.class01.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure ERROR 전략을 검증하는 예제
 *  - expectError()를 사용하여 에러가 발생되었는지 검증
 *  - verifyThenAssertThat()을 사용하여 검증 이후에 assertion method 를 사용하여 추가 검증을 할 수 있다.
 *  - hasDiscardedElements()를 사용하여 discard된 데이터가 있는지 검증한다. OverflowException이 발생할 때 2가 discard된다.
 *  - hasDiscarded()를 사용하여 discard된 데이터가 무엇인지 검증한다. OverflowException이 발생할 때 2가 discard된다.
 *  - hasDroppedElements()을 사용하여 Hooks.onNextDropped()으로 Drop된 데이터가 있는지를 검증한다.
 *  - hasDropped()를 사용하여 Hooks.onNextDropped()으로 Drop된 데이터가 무엇인지 검증한다.
 */

/**
 * Discard (폐기):
 * discard는 주로 backpressure 정책에 의해 구독자가 요청하지 않은 데이터를 버릴 때 발생합니다.
 * StepVerifier에서 hasDiscardedElements()나 hasDiscarded(2)와 같은 메서드는 구독자가 요청하지 않아 처리되지 않고 버려진 데이터가 무엇인지 검증하는 데 사용됩니다.
 * 보통 discard된 데이터는 구독자가 수신할 의도가 전혀 없는 데이터로, 요청된 수 만큼의 데이터만 처리하기 위해 나머지가 폐기되는 경우에 해당합니다.
 *
 * Drop (삭제):
 * drop은 에러나 예외 상황 등으로 인해 구독자가 데이터를 받을 수 없거나, 요청한 데이터 외에 추가로 발생한 데이터가 제거될 때 발생합니다.
 * StepVerifier에서 hasDroppedElements()나 hasDropped(3, 4, 5...) 같은 메서드는 에러 발생 후 처리되지 않고 버려진 데이터를 검증하는 데 사용됩니다.
 * 즉, 구독자의 요청과 상관없이, 에러 상황 등에서 발생한 데이터가 삭제된 것을 나타냅니다.
 */
public class StepVerifierBackpressureTestExample02 {
    @Test
    public void generateNumberTest() {
        StepVerifier
                .create(BackpressureExample.generateNumberByErrorStrategy(), 1L)
                .thenConsumeWhile(num -> num >= 1)
                .expectError()
                .verifyThenAssertThat() // 검증 이후 추가 검증
                .hasDiscardedElements() // 폐기된데이터가 있는지 검증
                .hasDiscarded(2) // 어떤 데이터가 폐기되었는지 구체적으로 검증
                .hasDroppedElements()
                .hasDropped(3, 4, 5, 6, 98, 99, 100);
    }
}
