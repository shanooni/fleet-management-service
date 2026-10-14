package io.shanoon.fleetmanagementsystem.message.consumer;

import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.repository.IVehicleRepository;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class VehicleHistoricalDataSubscriber implements MessageListener {

    private static final Logger log =  LoggerFactory.getLogger(VehicleHistoricalDataSubscriber.class);
    private final IVehicleService vehicleService;
    private final JacksonJsonRedisSerializer<VehicleStatusUpdate> serializer;

    public VehicleHistoricalDataSubscriber(IVehicleService vehicleService, JacksonJsonRedisSerializer<VehicleStatusUpdate> serializer)
    {
        this.vehicleService = vehicleService;
        this.serializer = serializer;
    }

    @Override
    public void onMessage(Message message, byte @Nullable [] pattern)
    {
        var update = serializer.deserialize(message.getBody());
        vehicleService.createVehicleHistoryFromUpdate(update);
    }
}
