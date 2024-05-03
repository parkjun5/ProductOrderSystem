//package kr.co._39cm.homework.support.config;
//
//import kr.co._39cm.homework.support.jpa.hibernate.DomainEventMethodInterceptor;
//import org.springframework.aop.PointcutAdvisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class InterceptorConfig {
//
//    @Bean
//    public DomainEventMethodInterceptor domainEventMethodInterceptor(ApplicationEventPublisher publisher) {
//        return new DomainEventMethodInterceptor(publisher);
//    }
//
//    @Bean
//    public PointcutAdvisor pointcutAdvisor(DomainEventMethodInterceptor interceptor) {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* kr.co._39cm.homework.order.v2.application.*.*(..))");
//        return new DefaultPointcutAdvisor(pointcut, interceptor);
//    }
//}
