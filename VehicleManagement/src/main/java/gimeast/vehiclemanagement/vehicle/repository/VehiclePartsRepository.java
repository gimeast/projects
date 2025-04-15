package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehiclePartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehiclePartsRepository extends JpaRepository<VehiclePartsEntity, Long> {
    VehiclePartsEntity findByName(String name);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO(e.idx, e.name) " +
            "FROM VehiclePartsEntity e")
    List<VehiclePartsDTO> findAllDTO();
}
