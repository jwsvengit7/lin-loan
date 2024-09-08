package com.lin.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfiguration {
    @Bean
    public Executor asyncExecutorTaskConfiguration(){
        ThreadPoolTaskExecutor taskExecutorConfiguration = new ThreadPoolTaskExecutor();
        taskExecutorConfiguration.setCorePoolSize(4);
        taskExecutorConfiguration.setQueueCapacity(150);
        taskExecutorConfiguration.setMaxPoolSize(4);
        taskExecutorConfiguration.setThreadNamePrefix("AsyncExecutor");
        taskExecutorConfiguration.initialize();
        return taskExecutorConfiguration;
    }
}
