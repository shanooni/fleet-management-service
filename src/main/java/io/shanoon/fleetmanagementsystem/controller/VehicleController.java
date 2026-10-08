package io.shanoon.fleetmanagementsystem.controller;

import io.shanoon.fleetmanagementsystem.message.sender.VehicleStatusPublisher;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

//    @GetMapping
//    public String publish(@RequestParam String message){
//        vehiclePublisher.publishMessage("vehicle-update", message);
//        return "Message sent";
//    }

    @PostMapping("/{vehicleId}/status")
    public ResponseEntity<Void> updateStatus( @PathVariable String vehicleId,
                                              @Valid @RequestBody VehicleStatusUpdate update)
    {
        vehicleService.publishStatus(update);
        vehicleService.processStatusUpdate(vehicleId,update);
        return ResponseEntity.accepted().build();
    }
}
