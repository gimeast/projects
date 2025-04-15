package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO;
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

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO(trim_parts.idx, parts.name) " +
            "FROM VehiclePartsEntity parts " +
            "JOIN VehicleTrimPartsEntity trim_parts ON trim_parts.parts = parts " +
            "JOIN VehicleEntity vehicle ON vehicle.trim = trim_parts.trim " +
            "WHERE vehicle.idx = :memberVehicleIdx")
    List<VehicleMaintenancePartsDTO> findAllDTOByMemberVehicleIdx(Long memberVehicleIdx);
}
