package io.shanoon.fleetmanagementsystem.service.Interface;

import io.shanoon.fleetmanagementsystem.model.Vehicle;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleUpdateDto;
import jdk.jshell.spi.ExecutionControl;

public interface IVehicleService {
    public VehicleUpdateDto createVehicleUpdate(Vehicle vehicle) throws ExecutionControl.NotImplementedException;
}
