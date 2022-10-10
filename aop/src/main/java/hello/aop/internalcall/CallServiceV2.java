package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext; //빈을 로드하기에는 너무 큰 클래스
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }

    public void external() {
        log.info("call external");
        CallServiceV2 bean = callServiceV2ObjectProvider.getObject(); //지연 조회
        bean.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
