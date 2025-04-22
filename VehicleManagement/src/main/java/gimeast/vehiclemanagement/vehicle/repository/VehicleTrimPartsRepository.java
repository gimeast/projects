package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimPartsDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehiclePartsEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimPartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleTrimPartsRepository extends JpaRepository<VehicleTrimPartsEntity, Long> {
    VehicleTrimPartsEntity findByPartsAndTrim(VehiclePartsEntity vehiclePartsEntity, VehicleTrimEntity vehicleTrimEntity);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehicleTrimPartsDTO(" +
            "   e.idx, e.replacementInterval, " +
            "   new gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO(t.idx, t.drivetrain, t.fuelType, t.transmission), " +
            "   new gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO(p.idx, p.name) " +
            ") " +
            "FROM VehicleTrimPartsEntity e " +
            "JOIN e.trim t " +
            "JOIN e.parts p " +
            "WHERE t.idx = :trimIdx")
    List<VehicleTrimPartsDTO> findAllDTOByTrimIdx(Long trimIdx);

    @Modifying
    @Query("DELETE FROM VehicleTrimPartsEntity e WHERE e.trim.idx = :trimIdx")
    void deleteByTrimIdx(Long trimIdx);
}
