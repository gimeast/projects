package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.admin.repository.custom.VehicleSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleTrimRepository extends JpaRepository<VehicleTrimEntity, Long>, VehicleSearch {
    Optional<VehicleTrimEntity> findByModelAndDrivetrainAndFuelTypeAndTransmission(
            VehicleModelEntity model, String drivetrain, String fuelType, String transmission
    );

    int countByModel(VehicleModelEntity model);
}
