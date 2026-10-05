package io.shanoon.fleetmanagementsystem.message.sender;

import io.shanoon.fleetmanagementsystem.message.consumer.VehicleTelemetrySubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class VehiclePublisherConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       MessageListenerAdapter messageListenerAdapter)
    {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(messageListenerAdapter, new ChannelTopic("vehicle-update"));
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(VehicleTelemetrySubscriber vehicleTelemetrySubscriber){
        return new MessageListenerAdapter(vehicleTelemetrySubscriber, "handleMessage");
    }
}
