package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleBrandRepository extends JpaRepository<VehicleBrandEntity, Long> {
    Optional<VehicleBrandEntity> findByName(String name);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleBrandDTO(brand.idx, brand.name) " +
            "FROM VehicleBrandEntity brand")
    List<VehicleBrandDTO> findAllDTO();
}
