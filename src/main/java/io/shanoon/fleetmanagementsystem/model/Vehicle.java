package io.shanoon.fleetmanagementsystem.model;

import java.sql.Timestamp;
import java.util.UUID;

public record Vehicle(UUID id, String latitude, String longitude, int batteryPercentage, Timestamp updateTime) {
}
