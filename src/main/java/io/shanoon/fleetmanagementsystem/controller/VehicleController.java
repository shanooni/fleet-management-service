package io.shanoon.fleetmanagementsystem.controller;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import io.shanoon.fleetmanagementsystem.model.dto.VehicleStatusUpdate;
import io.shanoon.fleetmanagementsystem.service.Interface.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService){
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

    @GetMapping("/status")
    public ResponseEntity<List<VehicleStatus>>getVehiclesStatus()
    {
        var vehicleUpdates = vehicleService.getVehicleUpdate();
        return ResponseEntity.ok(vehicleUpdates);
    }

    @GetMapping("/{vehicleId}/history")
    public ResponseEntity<List<VehicleStatusHistory>> getVehicleHistory(@PathVariable String vehicleId)
    {
        var vehicleHistory = vehicleService.getVehicleHistoryFromId(vehicleId);
        return ResponseEntity.ok(vehicleHistory);
    }
}
