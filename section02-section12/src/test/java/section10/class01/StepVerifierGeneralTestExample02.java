package section10.class01;

import com.itvillage.section10.class00.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/***
 * expectNext()를 사용하여 emit 된 n 개의 데이터를 검증하는 예제
 */
public class StepVerifierGeneralTestExample02 {
    @Test
    public void sayHelloReactorTest() {
        StepVerifier
                .create(GeneralExample.sayHelloReactor())
                .expectSubscription() // 구독 정상 발생 검증
                .expectNext("Hello") // 첫번째 emit 데이터를 Hello로 기대함
                .expectNext("Reactor") // 두번째 emit 데이터를 Reactor로 기대함
                .expectComplete() // onComplete Signal 검증
                .verify(); // 검증 실행.
    }
}
