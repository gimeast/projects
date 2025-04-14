package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleModelRepository extends JpaRepository<VehicleModelEntity, Long> {
    Optional<VehicleModelEntity> findByNameAndBrand(String name, VehicleBrandEntity brand);
    int countByBrand(VehicleBrandEntity brand);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleModelDTO(model.idx, model.name, model.year) " +
            "FROM VehicleModelEntity model " +
            "WHERE model.brand.idx = :brandIdx")
    List<VehicleModelDTO> findAllDTOListByBrandIdx(Long brandIdx);
}
