package kr.co._39cm.homework.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@EnableRetry
@Configuration
public class LockConfig {

    @Bean
    public Lock orderProductLock() {
        return new ReentrantLock();
    }

}
