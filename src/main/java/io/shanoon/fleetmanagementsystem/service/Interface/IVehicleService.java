package io.shanoon.fleetmanagementsystem.service.Interface;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;

import java.util.List;
import java.util.Optional;

public interface IVehicleService {
    void publishStatus(String vehicleId, VehicleStatusUpdate update);
    void processStatusUpdate(VehicleStatusUpdate update);
    void createVehicleHistoryFromUpdate(VehicleStatusUpdate update);
    Optional<VehicleStatus> getCurrentStatus(String vehicleId);
    List<VehicleStatus> getVehicleUpdate();
    List<VehicleStatusHistory> getVehicleHistoryFromId(String vehicleId);
}
