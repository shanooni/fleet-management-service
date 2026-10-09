package io.shanoon.fleetmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "vehicle_status_history")
public class VehicleStatusHistory {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) String vehicleStatusHistoryId;
    @NotNull
    private String vehicleId;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Double batteryPercentage;
    private Double speed;
    @NotNull
    private Instant eventTimestamp;
    @NotNull
    private Instant receivedAt;
}
