package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleModelRepository extends JpaRepository<VehicleModelEntity, Long> {
    Optional<VehicleModelEntity> findByNameAndBrand(String name, VehicleBrandEntity brand);
    int countByBrand(VehicleBrandEntity brand);
}
