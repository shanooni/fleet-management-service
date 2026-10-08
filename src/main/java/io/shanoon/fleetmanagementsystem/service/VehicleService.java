package io.shanoon.fleetmanagementsystem.service;

import io.shanoon.fleetmanagementsystem.message.consumer.VehicleCurrentStatusSubscriber;
import io.shanoon.fleetmanagementsystem.message.sender.VehicleStatusPublisher;
import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.repository.IVehicleRepository;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleStatusPublisher publisher;
    private final IVehicleRepository repository;
    public VehicleService(VehicleStatusPublisher vehicleStatusPublisher, IVehicleRepository repository){
        this.publisher = vehicleStatusPublisher;
        this.repository = repository;
    }

    @Override
    public void publishStatus(VehicleStatusUpdate update) {
        publisher.publishMessage(update);
    }

    @Override
    public void updateCurrentStatus(VehicleStatusUpdate update) {

    }

    @Override
    public void processStatusUpdate(VehicleStatusUpdate update) {
        var vehicleStatus = new VehicleStatus();
        vehicleStatus.setBatteryPercentage(update.batteryPercentage());
        vehicleStatus.setLatitude(update.latitude());
        vehicleStatus.setLongitude(update.longitude());
        vehicleStatus.setSpeed(update.speed());
        vehicleStatus.setTimestamp(update.timestamp());
        repository.save(vehicleStatus);
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
