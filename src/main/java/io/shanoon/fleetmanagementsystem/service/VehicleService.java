package io.shanoon.fleetmanagementsystem.service;

import io.shanoon.fleetmanagementsystem.message.consumer.VehicleCurrentStatusSubscriber;
import io.shanoon.fleetmanagementsystem.message.sender.VehicleStatusPublisher;
import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService implements IVehicleService {
    private final VehicleStatusPublisher publisher;
    private final VehicleCurrentStatusSubscriber statusSubscriber;
    public VehicleService(VehicleStatusPublisher vehicleStatusPublisher,
                          VehicleCurrentStatusSubscriber vehicleCurrentStatusSubscriber){
        this.publisher = vehicleStatusPublisher;
        this.statusSubscriber = vehicleCurrentStatusSubscriber;
    }

    @Override
    public void publishStatus(VehicleStatusUpdate update) {
        publisher.publishMessage(update);
    }

    @Override
    public void updateCurrentStatus(VehicleStatusUpdate update) {

    }

    @Override
    public void processStatusUpdate(String vehicleId, VehicleStatusUpdate update) {
        var vehicleStatus = new VehicleStatus(
                vehicleId,
                update.latitude(),
                update.longitude(),
                update.batteryPercentage(),
                update.speed(),
                update.timestamp()
        );
        System.out.println("received status update for vehicle: " + vehicleId);
    }

    @Override
    public VehicleStatusUpdate getCurrentStatus(UUID vehicleId) {
        return null;
    }

    @Override
    public List<VehicleStatusUpdate> getVehicleUpdate() {
        return List.of();
    }


}
