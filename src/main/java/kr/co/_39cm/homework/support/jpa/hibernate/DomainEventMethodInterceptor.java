//package kr.co._39cm.homework.support.jpa.hibernate;
//
//import io.micrometer.common.lang.Nullable;
//import kr.co._39cm.homework.support.domain.AggregateRoot;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.domain.AfterDomainEventPublication;
//import org.springframework.data.domain.DomainEvents;
//import org.springframework.data.util.AnnotationDetectionMethodCallback;
//import org.springframework.util.Assert;
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Supplier;
//
//
//public class DomainEventMethodInterceptor implements MethodInterceptor {
//
//    private final EventPublisher publisher;
//
//    public DomainEventMethodInterceptor(ApplicationEventPublisher publisher) {
//        this.publisher = new EventPublisher(publisher);
//    }
//
//    @Nullable
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        Object result = invocation.proceed();
//        if (invocation.getMethod().isAnnotationPresent(DomainEventTrigger.class)) {
//            Method method = invocation.getMethod();
//            DomainEventTrigger trigger = method.getAnnotation(DomainEventTrigger.class);
//            Object[] aggregates = filterAggregateInstances(trigger.domainClasses());
//            if (aggregates.length == 0) {
//                return result;
//            }
//            publisher.checkAndPublishEvents(invocation.getMethod(), aggregates);
//        }
//        return result;
//    }
//
//    public Object[] filterAggregateInstances(Class<?>[] domainClasses) {
//        return Arrays.stream(domainClasses)
//                     .filter(Objects::nonNull)
//                     .filter(AggregateRoot.class::isAssignableFrom)
//                     .toArray(Object[]::new);
//    }
//
//    static class EventPublisher {
//
//        private static final Map<Method, EventPublishingMethod> cache = new ConcurrentHashMap<>();
//        private final ApplicationEventPublisher publisher;
//
//        public EventPublisher(ApplicationEventPublisher publisher) {
//            this.publisher = publisher;
//        }
//
//        public void checkAndPublishEvents(Method method, Object[] aggregates) {
//            Assert.notNull(method, "Method must not be null");
//            if (aggregates == null) return;
//
//            for (Object aggregate : aggregates) {
//                EventPublishingMethod eventPublishingMethod = cache.computeIfAbsent(method,
//                        unused -> EventPublishingMethod.of(aggregate));
//
//                if (eventPublishingMethod != null) {
//                    eventPublishingMethod.publishEventsFrom(aggregate, publisher);
//                }
//            }
//        }
//
//    }
//
//
//    static class EventPublishingMethod {
//
//        private static final @SuppressWarnings("null") EventPublishingMethod NONE
//                = new EventPublishingMethod(Object.class, null, null);
//
//        private final Class<?> type;
//        private final Method publishingMethod;
//        private final @Nullable Method clearingMethod;
//
//        EventPublishingMethod(Class<?> type, Method publishingMethod, @Nullable Method clearingMethod) {
//            this.type = type;
//            this.publishingMethod = publishingMethod;
//            this.clearingMethod = clearingMethod;
//        }
//
//        /**
//         * Publishes all events in the given aggregate root using the given {@link ApplicationEventPublisher}.
//         *
//         * @param aggregateRoot can be {@literal null}.
//         * @param publisher     must not be {@literal null}.
//         */
//        public void publishEventsFrom(Object aggregateRoot, ApplicationEventPublisher publisher) {
//
//            boolean instance = type.isInstance(aggregateRoot);
//            if (!instance) {
//                return;
//            }
//
//            for (Object event : asCollection(ReflectionUtils.invokeMethod(publishingMethod, aggregateRoot))) {
//                publisher.publishEvent(event);
//            }
//
//            if (clearingMethod != null) {
//                ReflectionUtils.invokeMethod(clearingMethod, aggregateRoot);
//            }
//        }
//
//        private static <T extends Annotation> AnnotationDetectionMethodCallback<T> getDetector(Class<?> type,
//                                                                                              Class<T> annotation) {
//
//            AnnotationDetectionMethodCallback<T> callback = new AnnotationDetectionMethodCallback<>(annotation);
//            ReflectionUtils.doWithMethods(type, callback);
//
//            return callback;
//        }
//
//        /**
//         * Creates a new {@link EventPublishingMethod} using the given pre-populated
//         * {@link AnnotationDetectionMethodCallback} looking up an optional clearing method from the given callback.
//         */
//        public static EventPublishingMethod of(Object type) {
//            if (!(type instanceof Class)) {
//                throw new IllegalArgumentException("The provided object is not a Class object");
//            }
//
//            Class<?> aClass = (Class<?>) type;
//            AnnotationDetectionMethodCallback<?> publishing = getDetector(aClass, DomainEvents.class);
//            Supplier<AnnotationDetectionMethodCallback<?>> clearing = () -> getDetector(aClass, AfterDomainEventPublication.class);
//
//            if (!publishing.hasFoundAnnotation()) {
//                return EventPublishingMethod.NONE;
//            }
//
//            Method eventMethod = publishing.getRequiredMethod();
//            ReflectionUtils.makeAccessible(eventMethod);
//
//            return new EventPublishingMethod(aClass, eventMethod, getClearingMethod(clearing.get()));
//        }
//
//        /**
//         * Returns the {@link Method} supposed to be invoked for event clearing or {@literal null} if none is found.
//         *
//         * @param clearing must not be {@literal null}.
//         */
//        @Nullable
//        private static Method getClearingMethod(AnnotationDetectionMethodCallback<?> clearing) {
//
//            if (!clearing.hasFoundAnnotation()) {
//                return null;
//            }
//
//            Method method = clearing.getRequiredMethod();
//            ReflectionUtils.makeAccessible(method);
//
//            return method;
//        }
//
//        private static Iterable<Object> asCollection(@Nullable Object source) {
//
//            if (source == null) {
//                return Collections.emptyList();
//            }
//
//            if (source instanceof Collection) {
//                return (Collection<Object>) source;
//            }
//
//            return Collections.singletonList(source);
//        }
//
//    }
//}