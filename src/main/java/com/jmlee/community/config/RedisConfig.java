package com.jmlee.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Description TODO
 * @Author jmlee
 * @Date 2020/5/3 22:11
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());

        // 设置普通value序列化方式
        template.setValueSerializer(RedisSerializer.json());

        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();

        return template;
    }
}
