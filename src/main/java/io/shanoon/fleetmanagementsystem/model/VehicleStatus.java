package io.shanoon.fleetmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "vehicle_current_status")
public class VehicleStatus {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) String vehicleId;
    @NotNull
    @NotBlank
    @DecimalMax("90")
    @DecimalMin("-90")
    private Double longitude;
    @NotNull
    @NotBlank
    @DecimalMin("-90")
    @DecimalMax("90")
    private Double latitude;
    @NotBlank
    @DecimalMax("100.0")
    @DecimalMin("0.0")
    private Double batteryPercentage;
    @NotNull
    @NotBlank
    @PositiveOrZero
    private Double speed;
    @NotNull
    @NotBlank
    private Instant eventTimestamp;

}