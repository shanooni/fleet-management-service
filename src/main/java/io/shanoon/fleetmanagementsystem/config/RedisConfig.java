package io.shanoon.fleetmanagementsystem.config;

import io.shanoon.fleetmanagementsystem.message.consumer.VehicleCurrentStatusSubscriber;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       VehicleCurrentStatusSubscriber subscriber,
                                                                       ChannelTopic channelTopic)
    {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(
                subscriber,
                channelTopic
        );
        return container;
    }

    @Bean
    public MessageListenerAdapter vehicleCurrentStatusUpdateListenerAdapter(VehicleCurrentStatusSubscriber vehicleCurrentStatusSubscriber){
        return new MessageListenerAdapter(vehicleCurrentStatusSubscriber, "vehicleStatusUpdate");
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

    @Bean
    public ChannelTopic vehicleStatusTopic(){
        return new ChannelTopic("vehicle-status");
    }

    @Bean
    public JacksonJsonRedisSerializer<VehicleStatusUpdate> vehicleStatusUpdateJacksonJsonRedisSerializer(){
        return new JacksonJsonRedisSerializer<>(VehicleStatusUpdate.class);
    }
}
