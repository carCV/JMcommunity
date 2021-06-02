package com.jmlee.community.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Description TODO
 * @Author jmlee
 * @Date 2020/5/3 22:11
 * @Version 1.0
 */
@Configuration
public class RedisConfig {


    /**
     * Spring 为我们提供了一个 RedisTemplate 来进行对 Redis 的操作，但是 RedisTemplate 默认配置的是使用Java本机序列化。
     * 这种序列化方式，对于操作字符串或数字来说，用起来还行，但是如果要对对象操作，就不是那么的方便了。
     *
     * 重写 RedisTemplate 序列化
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // redis序列化器
        RedisSerializer<Object> serializer = redisSerializer();

        template.setConnectionFactory(factory);
        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());

        // 设置普通value序列化方式Jackson2JsonRedisSerializer
        template.setValueSerializer(serializer);

        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置hash的value的序列化方式为Jackson2JsonRedisSerializer
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }


    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 必须设置，否则无法将JSON转化为对象，会转化成Map类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // Springboot 2.2+ 版本使用此方式
        // 必须设置，否则无法将JSON转化为对象，会转化成Map类型
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);

        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    /**
     * 重写Cache序列化
     * @param factory
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置默认过期时间 30min
                .entryTtl(Duration.ofMinutes(30))
                // 设置 key 和 value 的序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


}
