package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehiclePartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePartsRepository extends JpaRepository<VehiclePartsEntity, Long> {
}
