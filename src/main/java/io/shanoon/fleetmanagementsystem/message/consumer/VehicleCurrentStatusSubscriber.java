package io.shanoon.fleetmanagementsystem.message.consumer;

import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import io.shanoon.fleetmanagementsystem.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.annotation.RedisListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleCurrentStatusSubscriber {
    private static final Logger log =  LoggerFactory.getLogger(VehicleCurrentStatusSubscriber.class);
    private IVehicleService vehicleService;

    public void vehicleStatusUpdate(VehicleStatusUpdate update){
        vehicleService.processStatusUpdate("VH-1001",update);
    }

//    public List<VehicleStatusUpdate> getVehicleStatusUpdate(){
//        return vehicleService.getVehicleUpdate();
//    }
}
