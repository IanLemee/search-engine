package com.tech.ian.search_engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import  redis.clients.jedis.RedisClient;

@Configuration
public class RedisConnection {

    @Bean
    public RedisClient redisClient() {
        return RedisClient.builder().fromURI("redis://localhost:6379").build();
    }
}
