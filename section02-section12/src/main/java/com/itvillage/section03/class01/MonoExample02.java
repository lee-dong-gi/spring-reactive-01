package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * Mono 기본 개념 예제
 *  - 원본 데이터의 emit 없이 onComplete signal 만 emit 한다.
 */
public class MonoExample02 {
    public static void main(String[] args) {
        Mono.empty() // 업스트림
                .subscribe(
                        data -> Logger.info("# emitted data: {}", data), // 데이터 처리
                        error -> {}, // 만약에 에러가 발생한다면
                        () -> Logger.info("# emitted onComplete signal") // 업스트림에서 모든 데이터를 emit한 후에 onComplete signal을 받아 처리
                );
    }
}
