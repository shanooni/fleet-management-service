package io.shanoon.fleetmanagementsystem.controller;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping("/{vehicleId}/status")
    public ResponseEntity<Void> updateStatus( @PathVariable String vehicleId,
                                              @Valid @RequestBody VehicleStatusUpdate update)
    {
        vehicleService.publishStatus(vehicleId, update);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{vehicleId}/status")
    public ResponseEntity<VehicleStatus> getCurrentStatus(@PathVariable String vehicleId) {
        return vehicleService.getCurrentStatus(vehicleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
