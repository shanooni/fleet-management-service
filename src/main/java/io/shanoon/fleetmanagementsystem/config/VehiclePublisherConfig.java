package io.shanoon.fleetmanagementsystem.config;

import io.shanoon.fleetmanagementsystem.message.consumer.VehicleCurrentStatusSubscriber;
import io.shanoon.fleetmanagementsystem.message.consumer.VehicleHistoricalDataSubscriber;
import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class VehiclePublisherConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory)
    {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    public MessageListenerAdapter vehicleCurrentStatusUpdateListenerAdapter(VehicleCurrentStatusSubscriber vehicleCurrentStatusSubscriber){
        return new MessageListenerAdapter(vehicleCurrentStatusSubscriber, "vehicleStatusUpdate");
    }

    @Bean
    VehicleCurrentStatusSubscriber vehicleCurrentStatusSubscriber(){
        return new VehicleCurrentStatusSubscriber();
    }

    @Bean
    RedisTemplate<String, VehicleStatusUpdate> template(RedisConnectionFactory connectionFactory){
        var template = new RedisTemplate<String, VehicleStatusUpdate>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        var jsonRedisSerializer = new JacksonJsonRedisSerializer<>(VehicleStatusUpdate.class);

        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        return template;
    }
}
