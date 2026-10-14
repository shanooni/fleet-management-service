package io.shanoon.fleetmanagementsystem.config;

import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Collections;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory)
    {
        var redisCacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new JacksonJsonRedisSerializer<>(VehicleStatusUpdate.class))
                );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfig)
                .build();
    }

    @Bean
    public SimpleKeyGenerator keyGenerator()
    {
        return new SimpleKeyGenerator();
    }
}
