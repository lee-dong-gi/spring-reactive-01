package com.itvillage.section04.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class HotSequenceExample {
    public static void main(String[] args) {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                        .delayElements(Duration.ofSeconds(1)).share();  //  1초에 한번씩 emit, share() 원본 Flux를 여러 Subscriber가 공유한다.

        concertFlux.subscribe(singer -> Logger.info("# Subscriber1 is watching {}'s song.", singer)); // 모두 출력

        TimeUtils.sleep(2500);

        concertFlux.subscribe(singer -> Logger.info("# Subscriber2 is watching {}'s song.", singer)); // "Singer C", "Singer D", "Singer E" 출력

        TimeUtils.sleep(3000); // 메인스레드가 종료되지 않게 하는 용도
    }
}
