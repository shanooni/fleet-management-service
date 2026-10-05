package io.shanoon.fleetmanagementsystem.controller;

import io.shanoon.fleetmanagementsystem.message.sender.VehiclePublisher;
import io.shanoon.fleetmanagementsystem.model.Vehicle;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleUpdateDto;
import io.shanoon.fleetmanagementsystem.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehiclePublisher vehiclePublisher;

    @GetMapping("/api/publish/vehicle")
    public String publish(@RequestParam String message){
        vehiclePublisher.publishMessage("vehicle-update", message);
        return "Message sent";
    }

    @PostMapping("/api/vehicles")
    public ResponseEntity<VehicleUpdateDto> createVehicleUpdate(@RequestBody Vehicle vehicle){
        var vehicleUpdate = vehicleService.createVehicleUpdate(vehicle);
        return new ResponseEntity<>(vehicleUpdate,HttpStatus.CREATED);
    }
}
