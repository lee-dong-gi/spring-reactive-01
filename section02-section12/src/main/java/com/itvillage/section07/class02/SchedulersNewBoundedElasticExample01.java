package com.itvillage.section07.class02;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.newBoundedElastic()을 적용
 */
public class SchedulersNewBoundedElasticExample01 {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers
                // 각각 스레드개수, 각 스레드별 대기작업 큐의 크기, 스레드의 이름
                // 스레드 개수만큼 큐가 할당됨
                .newBoundedElastic(2, 2, "I/O-Thread");
        Mono<Integer> mono =
                    Mono
                        .just(1)
                        .subscribeOn(scheduler);

        Logger.info("# Start");

        mono.subscribe(data -> { // I/O-Thread-1 으로 수행
            Logger.onNext("subscribe 1 doing", data);
            TimeUtils.sleep(3000L);
            Logger.onNext("subscribe 1 done", data);
        });

        mono.subscribe(data -> { // I/O-Thread-2 으로 수행
            Logger.onNext("subscribe 2 doing", data);
            TimeUtils.sleep(3000L);
            Logger.onNext("subscribe 2 done", data);
        });

        mono.subscribe(data -> { // I/O-Thread-1 Queue 0번째에서 대기 후 I/O-Thread-1 스레드가 비면 처리
            Logger.onNext("subscribe 3 doing", data);
        });

        mono.subscribe(data -> { // I/O-Thread-2 Queue 0번째에서 대기 후 I/O-Thread-2 스레드가 비면 처리
            Logger.onNext("subscribe 4 doing", data);
        });

        mono.subscribe(data -> { // I/O-Thread-1 Queue 1번째에서 대기 후 I/O-Thread-1 스레드가 비면 처리
            Logger.onNext("subscribe 5 doing", data);
        });

        mono.subscribe(data -> { // I/O-Thread-2 Queue 1번째에서 대기 후 I/O-Thread-2 스레드가 비면 처리
            Logger.onNext("subscribe 6 doing", data);
        });

        // 더이상 사용할 스레드도 없고 대기할 Queue도 없을 경우 Exception 발생
//        mono.subscribe(data -> {
//            Logger.onNext("subscribe 7 doing", data);
//        });

//        TimeUtils.sleep(4000L);
//        scheduler.dispose(); // newBoundedElastic와 같이 new로 생성된 경우 일정시간동안 종료되지 않음 그래서 해당 구문으로 작업이 끝난 후 즉시 종료 할 수 있음
    }
}
