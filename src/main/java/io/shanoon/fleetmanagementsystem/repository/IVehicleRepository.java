package io.shanoon.fleetmanagementsystem.repository;

import io.shanoon.fleetmanagementsystem.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleStatus, String> {
}
