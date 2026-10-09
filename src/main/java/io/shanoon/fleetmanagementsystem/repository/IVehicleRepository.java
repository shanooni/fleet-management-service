package io.shanoon.fleetmanagementsystem.repository;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleStatus, String> {
    @Modifying
    @Query(value = """
            
            INSERT INTO vehicle_current_status(vehicle_id, latitude, longitude, battery_percentage, speed, event_timestamp)
                    VALUES (:vehicleId, :latitude, :longitude, :batteryPercentage, :speed ,:eventTimestamp)
                    ON CONFLICT (vehicle_id)
                    DO UPDATE SET latitude = EXCLUDED.latitude,
                                  longitude = EXCLUDED.longitude,
                                  battery_percentage = EXCLUDED.battery_percentage, speed = EXCLUDED.speed,
                                  event_timestamp = EXCLUDED.event_timestamp
                    WHERE EXCLUDED.event_timestamp > vehicle_current_status.event_timestamp
            """, nativeQuery = true)
    int upsertIfNewer(
            @Param("vehicleId") String vehicleId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("batteryPercentage") Double batteryPercentage,
            @Param("speed") Double speed,
            @Param("eventTimestamp" )Instant eventTimestamp
            );
    }

