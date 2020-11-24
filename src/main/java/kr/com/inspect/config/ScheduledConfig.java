package kr.com.inspect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Scheduler 설정
 * @author Wooyoung Lee
 * @version 1.0
 *
 */

@Configuration
@EnableScheduling
public class ScheduledConfig {
    /**
     * scheduler 설정을 하고 리턴
     * @return scheduler 반환
     */
    @Bean
    public TaskScheduler scheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        return scheduler;
    }
}
