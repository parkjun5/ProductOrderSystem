package kr.co.system.homework.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsyncConfig {

    private static final int DURATION = 5;
    private static final int QUEUE_CAPACITY = 10000;
    private static final String THREAD_NAME_PREFIX = "AsyncThread-";

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int corePoolSize = Runtime.getRuntime().availableProcessors() / 2;
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(corePoolSize * 2);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds((int) TimeUnit.SECONDS.toSeconds(DURATION));
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

        executor.initialize();
        return executor;
    }
}
