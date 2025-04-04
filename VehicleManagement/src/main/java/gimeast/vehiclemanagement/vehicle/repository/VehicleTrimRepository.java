package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleTrimRepository extends JpaRepository<VehicleTrimEntity, Long> {
    Optional<VehicleTrimEntity> findByModelAndDrivetrainAndFuelTypeAndTransmission(
            VehicleModelEntity model, String drivetrain, String fuelType, String transmission
    );
}
