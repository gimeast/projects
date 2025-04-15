package gimeast.vehiclemanagement.vehicle.repository;

import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    @Query("SELECT new gimeast.vehiclemanagement.vehicle.dto.VehicleDTO(" +
            "v.idx, v.numberPlate, v.kilometers, " +
                "new gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO(b.idx, b.name), " +
                "new gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO(m.idx, m.name, m.year), " +
                "new gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO(t.idx, t.drivetrain, t.fuelType, t.transmission)) " +
            "FROM VehicleEntity v " +
            "JOIN v.brand b " +
            "JOIN v.model m " +
            "JOIN v.trim t " +
            "WHERE v.member.mid = :mid")
    List<VehicleDTO> findVehicleDTOListByMid(@Param("mid") String mid);

}
