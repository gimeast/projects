package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleModelRepository extends JpaRepository<VehicleModelEntity, Long> {
    Optional<VehicleModelEntity> findByNameAndBrand(String name, VehicleBrandEntity brand);
    int countByBrand(VehicleBrandEntity brand);
}
