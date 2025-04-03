package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleBrandRepository extends JpaRepository<VehicleBrandEntity, Long> {
    Optional<VehicleBrandEntity> findByName(String name);
}
