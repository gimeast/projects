package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleMaintenanceEntity;
import gimeast.vehiclemanagement.vehicle.repository.custom.VehicleSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenanceEntity, Long>, VehicleSearch {
}
