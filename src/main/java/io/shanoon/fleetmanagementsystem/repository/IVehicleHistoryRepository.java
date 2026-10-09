package io.shanoon.fleetmanagementsystem.repository;

import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleHistoryRepository extends JpaRepository<VehicleStatusHistory, String> {
}
