package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleMaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenanceEntity, Long> {
    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO( " +
            "   maintenance.idx," +
            "   new gimeast.vehiclemanagement.vehicle.dto.VehicleTrimPartsDTO( " +
            "       maintenance.trimParts.idx, " +
            "       maintenance.trimParts.replacementInterval, " +
            "       new gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO(maintenance.trimParts.trim), " +
            "       new gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO(maintenance.trimParts.parts.idx, maintenance.trimParts.parts.name) " +
            "   ), " +
            "   maintenance.kilometers," +
            "   maintenance.memo," +
            "   maintenance.regDate" +
            ") " +
            "FROM VehicleMaintenanceEntity maintenance " +
            "WHERE maintenance.vehicle.idx = :memberVehicleIdx")
    List<VehicleMaintenanceDTO> findByMemberVehicleIdx(Long memberVehicleIdx);
}
