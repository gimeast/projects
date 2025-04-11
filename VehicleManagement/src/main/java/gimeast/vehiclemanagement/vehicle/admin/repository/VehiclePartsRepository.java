package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.entity.VehiclePartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePartsRepository extends JpaRepository<VehiclePartsEntity, Long> {
}
