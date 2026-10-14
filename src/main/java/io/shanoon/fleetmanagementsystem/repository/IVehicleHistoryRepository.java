package io.shanoon.fleetmanagementsystem.repository;

import io.shanoon.fleetmanagementsystem.model.VehicleStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVehicleHistoryRepository extends JpaRepository<VehicleStatusHistory, String> {

    List<VehicleStatusHistory> findAllByVehicleId(String vehicleId);
}
