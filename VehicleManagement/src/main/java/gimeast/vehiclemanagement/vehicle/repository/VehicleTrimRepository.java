package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.repository.custom.VehicleSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleTrimRepository extends JpaRepository<VehicleTrimEntity, Long>, VehicleSearch {
    Optional<VehicleTrimEntity> findByModelAndDrivetrainAndFuelTypeAndTransmission(
            VehicleModelEntity model, String drivetrain, String fuelType, String transmission
    );

    int countByModel(VehicleModelEntity model);
}
