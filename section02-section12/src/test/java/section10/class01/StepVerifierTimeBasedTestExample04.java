package section10.class01;

import com.itvillage.section10.class00.TimeBasedExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

import java.time.Duration;

/**
 * expectNoEvent(Duration)으로 지정된 대기 시간동안 이벤트가 없을을 확인하는 예제
 */
public class StepVerifierTimeBasedTestExample04 {
    @Test
    public void getCOVID19CountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedExample.getVoteCount(
                                Flux.interval(Duration.ofMinutes(1)) // 1분마다 data를 emit
                        )
                )
                .expectSubscription()
                .expectNoEvent(Duration.ofMinutes(1)) // 1분동안 아무런 이벤트가 발생하지 않음
                .expectNext(Tuples.of("중구", 15400)) // 첫번째 튜플
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext(Tuples.of("서초구", 20020))
                .expectNoEvent(Duration.ofSeconds(30L))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext(Tuples.of("강서구", 32040))
                .expectNoEvent(Duration.ofSeconds(30L))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}