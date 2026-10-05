package io.shanoon.fleetmanagementsystem.message.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehiclePublisher {

    private final StringRedisTemplate redisTemplate;

    public VehiclePublisher(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void publishMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
