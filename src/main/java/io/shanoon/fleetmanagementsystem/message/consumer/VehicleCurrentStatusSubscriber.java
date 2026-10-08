package io.shanoon.fleetmanagementsystem.message.consumer;

import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Component;


@Component
public class VehicleCurrentStatusSubscriber implements MessageListener {

    private static final Logger log =  LoggerFactory.getLogger(VehicleCurrentStatusSubscriber.class);

    private final JacksonJsonRedisSerializer<VehicleStatusUpdate> serializer;
    private final IVehicleService vehicleService;

    public VehicleCurrentStatusSubscriber(JacksonJsonRedisSerializer<VehicleStatusUpdate> vehicleStatusUpdateJacksonJsonRedisSerializer,
                                          IVehicleService vehicleService){
        this.serializer = vehicleStatusUpdateJacksonJsonRedisSerializer;
        this.vehicleService = vehicleService;
    }

    @Override
    public void onMessage(Message message, byte @Nullable [] pattern) {
        var update = serializer.deserialize(message.getBody());
        vehicleService.processStatusUpdate(update);
    }
}
