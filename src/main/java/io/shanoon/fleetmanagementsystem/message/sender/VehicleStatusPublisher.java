package io.shanoon.fleetmanagementsystem.message.sender;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatusPublisher {

    private final RedisTemplate<String, VehicleStatusUpdate> redisTemplate;
    public VehicleStatusPublisher(RedisTemplate<String, VehicleStatusUpdate> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void publishMessage(String vehicleId, VehicleStatusUpdate update) {
        update.withVehicleId(vehicleId);
        redisTemplate.convertAndSend("vehicle-status", update);
    }
}
