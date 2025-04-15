package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleBrandRepository extends JpaRepository<VehicleBrandEntity, Long> {
    Optional<VehicleBrandEntity> findByName(String name);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO(brand.idx, brand.name) " +
            "FROM VehicleBrandEntity brand")
    List<VehicleBrandDTO> findAllDTO();
}
