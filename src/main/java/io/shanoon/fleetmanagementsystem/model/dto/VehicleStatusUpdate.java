package io.shanoon.fleetmanagementsystem.model.dto;

import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

public record VehicleStatusUpdate(
        @NotNull
        @DecimalMax("90.0")
        @DecimalMin("-90.0")
        Double longitude,
        @NotNull
        @DecimalMax("90.0")
        @DecimalMin("-90.0")
        Double latitude,
        @NotNull
        @DecimalMax("100.0")
        @DecimalMin("0.0")
        Double batteryPercentage,
        @NotNull
        @PositiveOrZero
        Double speed,
        @NotNull
        Instant timestamp)
{ }
