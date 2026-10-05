package io.shanoon.fleetmanagementsystem.service;

import io.shanoon.fleetmanagementsystem.model.Vehicle;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleUpdateDto;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements IVehicleService {
    public VehicleUpdateDto createVehicleUpdate(Vehicle vehicle)  {
        return new VehicleUpdateDto(vehicle.id());
    }
}
