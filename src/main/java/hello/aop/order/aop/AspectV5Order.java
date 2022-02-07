package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {

        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature()); // join point signature
            return joinPoint.proceed();
        }

    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {

        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws  Throwable {
            try {
                log.info("[transaction start] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[transaction end] {}", joinPoint.getSignature());
                return result;
            } catch (Exception ex) {
                log.info("[transaction rollback] {}", joinPoint.getSignature());
                throw ex;
            } finally {
                log.info("[resource release] {}", joinPoint.getSignature());
            }
        }

    }

}
