package com.itvillage.section05.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Subscriber가 처리 가능한 만큼의 request 개수를 조절하는 Backpressure 예제
 */
public class BackpressureExample01 {
    public static void main(String[] args) {
        Flux.range(1, 5) // 1 ~ 5 까지 Downstream에 emit함
            .doOnNext(Logger::doOnNext) // upstream에서 emit한 데이터를 출력
            .doOnRequest(Logger::doOnRequest) // subscriber에서 요청한 데이터 개수를 출력
            .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) { // 구독 시점 실행
                    request(1); // 데이터 요청 개수
                }

                @Override
                protected void hookOnNext(Integer value) { // publisher에서 data를 emit하면 emit 된 데이터들 전달 받아 처리
                    TimeUtils.sleep(2000L); // 처리시간
                    Logger.onNext(value);
                    request(1); // 데이터 처리가 끝나면 publisher에 요청할 데이터 개수
                }
            });
    }
}
