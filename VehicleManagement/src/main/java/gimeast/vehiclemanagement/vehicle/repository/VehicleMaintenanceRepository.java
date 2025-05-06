package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.entity.VehicleMaintenanceEntity;
import gimeast.vehiclemanagement.vehicle.repository.custom.VehicleSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenanceEntity, Long>, VehicleSearch {

    @Query("SELECT vm FROM VehicleMaintenanceEntity vm " +
            "JOIN (SELECT vm2.trimParts.idx AS trimPartsIdx, MAX(vm2.regDate) AS maxRegDate " +
            "      FROM VehicleMaintenanceEntity vm2 " +
            "      WHERE vm2.vehicle.idx = :vehicleIdx " +
            "      AND vm2.regDate BETWEEN :startDate AND :endDate " +
            "      GROUP BY vm2.trimParts.idx) subq " +
            "ON vm.trimParts.idx = subq.trimPartsIdx AND vm.regDate = subq.maxRegDate " +
            "WHERE vm.vehicle.idx = :vehicleIdx " +
            "AND vm.regDate BETWEEN :startDate AND :endDate")
    List<VehicleMaintenanceEntity> findLatestMaintenance(
            @Param("vehicleIdx") Long vehicleIdx,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
