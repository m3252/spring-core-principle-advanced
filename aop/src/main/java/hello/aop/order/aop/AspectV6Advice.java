package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

//    @Around("Pointcuts.allOrder()")
//    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
//        return joinPoint.proceed();
//    }
//
    //hello.aop.order 패지키와 하위 패키지이면서 클래스 이름 패턴이 *Service
    @Around("Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("Pointcuts.orderAndService()")
    public void deBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "Pointcuts.orderAndService()", returning = "result")
    public void doReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "Pointcuts.allOrder()", returning = "result")
    public void doReturning2(JoinPoint joinPoint, String result) {
        log.info("[AfterReturning2] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] {} message={}", joinPoint.getSignature(), ex);
    }

    @After("Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
