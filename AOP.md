# AOP 용어 정리

### 조인 포인트 (Join point)

- 어드바이스가 적용될 수 있는 위치 (프로그램 실행 중 지점)
  - 메소드 실행
  - 생성자 호출
  - 필드 값 접근
  - static 메소드 접근
- 조인 포인트는 추상적인 개념이다. AOP를 적용할 수 있는 모든 지점이라 생각할 것
- 스프링 AOP는 프록시 방식을 사용하므로 조인 포인트는 항상 메소드 실행 지점으로 제한되며 스프링 빈이여야 한다는 제약을 가짐


### 포인트컷(Pointcut)

- 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
- 주로 AspectJ 표현식을 사용해서 지정
- 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능
  - AspectJ 프레임워크를 직접쓰면 생성자나 필드같은 곳에도 AOP를 지정가능하다.


### 타겟(Target)

- 어드바이스 를 받는 객체, 포인트컷으로 결정

### 어드바이스(Advice)

- 부가 기능
- 특정 조인 포인트에서 Aspect에 의해 취해지는 조치
- Around(주변), Before(전), After(후)와 같은 다양한 종류의 어드바이스가 있음

### 애스펙트(Aspect)

- 어드바이스 + 포인트컷을 모듈화 한 것
- @Aspect 와 같음
- 여러 어드바이스와 포인트 컷이 함께 존재

### 어드바이저(Advisor)

- 하나의 어드바이스와 하나의 포인트 컷으로 구성
- 스프링 AOP에서만 사용되는 특별한 용어

### 위빙(Weaving)

- 포인트컷으로 결정한 타겟의 조인 포인트에 어드바이스를 적용하는 것
- 위빙을 통해서 핵심 기능 코드에 영향을 주지 않고 부가 기능을 추가 할 수 있음
- AOP 적용을 위해 애스펙트를 객체에 연결한 상태
  - 컴파일 타임
  - 로드 타임
  - 런타임, 스프링 AOP는 런타임 프록시 방식

### AOP 프록시

- AOP 기능을 구현하기 위해 만든 프록시 객체, 스프링에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시이다.

---

## 어드바이스 종류

- @Around
  - 메서드 호출 전후에 수행, 가장 강력한 어드바이스이다.
    - 조인 포인트 실행 여부 선택
    - 반환 값 변환
    - 예외 변환
- @Before
  - 조인 포인트 실행 이전에 실행
- @AfterReturning
  - 조인 포인트가 정상 완료 후 실행
- @AfterThrowing
  - 메서드가 예외를 던지는 경우 실행
- @After
  - 조인 포인틀가 정상 또는 예외에 관계없이 실행 (finally)

#### 어드바이스 적용 시점

```java
    @Aspect
    @Order(1)
    public static class TransactionAspect {
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

    }
```

#### JoinPoint, ProceedingJoinPoint

- ProceedingJoinPoint는 org.aspectj.lang.JoinPoint의 하위타입이다.
- JoinPoint 인터페이스의 주요 기능
  - getArgs()
    - 메서드 인수를 반환
  - getThis()
    - 프록시 객체를 반환
  - getTarget()
    - 대상 객체를 반환
  - getSignature()
    - 조인되는 메서드에 대한 설명을 반환
  - toString()
    - 조인되는 방법에 대한 유용한 설명을 인쇄

  
---



