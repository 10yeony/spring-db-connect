package kr.com.inspect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 *
 * @author Wooyoung Lee
 * @version 1.0
 *
 */

@Configuration
@EnableScheduling
public class ScheduledConfig {
    @Bean
    public TaskScheduler scheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        return scheduler;
    }
}
