package com.itvillage.section07.class02;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.newParallel()을 적용
 */
public class SchedulersNewParallelExample01 {
    public static void main(String[] args) {
        Mono<Integer> flux =
                    Mono
                        .just(1)
                            // 각각 스레드이름, CPU CORE 범위내 스레드 개수, 데몬 스레드로 동작할지 여부
                            // 데몬 스레드(Daemon Thread)는 일반 스레드와 달리 백그라운드에서 실행되며, JVM이 종료될 때 자동으로 정리되는 특성을 가진 스레드
                        .publishOn(Schedulers.newParallel("Parallel Thread", 4, true));


        flux.subscribe(data -> { // Parallel Thread-1 할당
            TimeUtils.sleep(5000L);
            Logger.onNext("subscribe 1", data);
        });

        flux.subscribe(data -> { // Parallel Thread-2 할당
            TimeUtils.sleep(4000L);
            Logger.onNext("subscribe 2", data);
        });

        flux.subscribe(data -> { // Parallel Thread-3 할당
            TimeUtils.sleep(3000L);
            Logger.onNext("subscribe 3", data);
        });

        flux.subscribe(data -> { // Parallel Thread-4 할당
            TimeUtils.sleep(2000L);
            Logger.onNext("subscribe 4", data);
        });

        TimeUtils.sleep(6000L);
    }
}
