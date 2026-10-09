package io.shanoon.fleetmanagementsystem.service;

import io.shanoon.fleetmanagementsystem.message.sender.VehicleStatusPublisher;
import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.repository.IVehicleHistoryRepository;
import io.shanoon.fleetmanagementsystem.repository.IVehicleRepository;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {
    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleStatusPublisher publisher;
    private final IVehicleRepository vehicleRepository;
    private final IVehicleHistoryRepository vehicleHistoryRepository;
    public VehicleService(VehicleStatusPublisher vehicleStatusPublisher,
                          IVehicleRepository repository,
                          IVehicleHistoryRepository vehicleHistoryRepository){
        this.publisher = vehicleStatusPublisher;
        this.vehicleRepository = repository;
        this.vehicleHistoryRepository = vehicleHistoryRepository;
    }

    @Override
    public void publishStatus(String vehicleId, VehicleStatusUpdate update) {
        publisher.publishMessage(vehicleId,update);
    }

    @Override
    public void updateCurrentStatus(VehicleStatusUpdate update) {

    }

    @Override
    @Transactional
    public void processStatusUpdate(VehicleStatusUpdate update) {
        createVehicleHistoryFromUpdate(update);
        upsertVehicleCurrentStatusFromUpdate(update);
    }


    @Override
    public Optional<VehicleStatus> getCurrentStatus(String vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    @Override
    public List<VehicleStatusUpdate> getVehicleUpdate() {
        return List.of();
    }


    private void createVehicleHistoryFromUpdate(VehicleStatusUpdate update){
        var receivedAt = Instant.now();
        var history = new VehicleStatusHistory();
        history.setVehicleId(update.vehicleId());
        history.setBatteryPercentage(update.batteryPercentage());
        history.setLatitude(update.latitude());
        history.setLongitude(update.longitude());
        history.setSpeed(update.speed());
        history.setEventTimestamp(update.eventTimestamp());
        history.setReceivedAt(receivedAt);
        log.info("history record {}",history.toString());
        vehicleHistoryRepository.save(history);
    }

    private void upsertVehicleCurrentStatusFromUpdate(VehicleStatusUpdate update){
        var rowChanged = vehicleRepository.upsertIfNewer(
                update.vehicleId(),
                update.latitude(),
                update.longitude(),
                update.batteryPercentage(),
                update.speed(),
                update.eventTimestamp()
        );

        log.info("change row value {}", rowChanged);

        if (rowChanged == 0){
            log.debug("Current status was unchanged, the event was not new {}", update.vehicleId());
        }
        else{
            log.debug("Updated current status for vehicle {} at {}", update.vehicleId(), update.eventTimestamp());
        }

    }

}
