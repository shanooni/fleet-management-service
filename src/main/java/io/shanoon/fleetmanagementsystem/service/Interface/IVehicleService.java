package io.shanoon.fleetmanagementsystem.service.Interface;

import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;

import java.util.List;
import java.util.UUID;

public interface IVehicleService {
    void publishStatus(VehicleStatusUpdate update);
    void updateCurrentStatus(VehicleStatusUpdate update);
    void processStatusUpdate(VehicleStatusUpdate update);
    VehicleStatusUpdate getCurrentStatus(UUID vehicleId);

    List<VehicleStatusUpdate> getVehicleUpdate();
}
