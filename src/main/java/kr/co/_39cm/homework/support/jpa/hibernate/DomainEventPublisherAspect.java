//package kr.co._39cm.homework.support.jpa.hibernate;
//
//import kr.co._39cm.homework.support.domain.AggregateRoot;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//
//@Aspect
//@Component
//public class DomainEventPublisherAspect {
//    private final ApplicationEventPublisher eventPublisher;
//
//    public DomainEventPublisherAspect(ApplicationEventPublisher eventPublisher) {
//        this.eventPublisher = eventPublisher;
//    }
//
//    @Pointcut("execution(* kr.co._39cm.homework..*.*(..)) && @annotation(transactional)")
//    public void transactionalMethods(Transactional transactional) {}
//
//    @AfterReturning(pointcut = "transactionalMethods(transactional)", returning = "result", argNames = "transactional,result")
//    public void afterTransactionalMethodExecution(Transactional transactional, Object result) {
//        if (result instanceof AggregateRoot) {
//            AggregateRoot aggregateRoot = (AggregateRoot) result;
//            aggregateRoot.publish(eventPublisher);
//        }
//    }
//}
