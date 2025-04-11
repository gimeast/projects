package gimeast.vehiclemanagement.vehicle.member.repository;

import gimeast.vehiclemanagement.vehicle.member.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.member.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleMemberRepository extends JpaRepository<VehicleEntity, Long> {
    @Query("SELECT new gimeast.vehiclemanagement.vehicle.member.dto.VehicleDTO(" +
            "v.idx, v.numberPlate, v.kilometers, " +
                "new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleBrandDTO(b.idx, b.name), " +
                "new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleModelDTO(m.idx, m.name, m.year), " +
                "new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleTrimDTO(t.idx, t.drivetrain, t.fuelType, t.transmission)) " +
            "FROM VehicleEntity v " +
            "JOIN v.brand b " +
            "JOIN v.model m " +
            "JOIN v.trim t " +
            "WHERE v.member.mid = :mid")
    List<VehicleDTO> findVehicleDTOListByMid(@Param("mid") String mid);

}
