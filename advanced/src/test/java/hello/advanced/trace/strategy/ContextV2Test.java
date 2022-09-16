package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략 패턴 적용 (동적 전달)
     */
    @Test
    void strategyV1() {
        ContextV2 contextV = new ContextV2();
        contextV.execute(new StrategyLogic1());
        contextV.execute(new StrategyLogic2());
    }

    /**
     * 전략 패턴 익명 내부 클래스 적용
     */
    @Test
    void strategyV2() {
        ContextV2 contextV = new ContextV2();
        contextV.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        contextV.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    /**
     * 전략 패턴 람다 적용
     */
    @Test
    void strategyV3() {
        ContextV2 contextV = new ContextV2();
        contextV.execute(() -> log.info("비즈니스 로직1 실행"));
        contextV.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}
