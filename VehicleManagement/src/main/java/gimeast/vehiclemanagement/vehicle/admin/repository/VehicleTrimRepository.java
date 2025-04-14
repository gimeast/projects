package gimeast.vehiclemanagement.vehicle.admin.repository;

import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.admin.repository.custom.VehicleSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleTrimRepository extends JpaRepository<VehicleTrimEntity, Long>, VehicleSearch {
    Optional<VehicleTrimEntity> findByModelAndDrivetrainAndFuelTypeAndTransmission(
            VehicleModelEntity model, String drivetrain, String fuelType, String transmission
    );

    int countByModel(VehicleModelEntity model);

    @Query("SELECT new gimeast.vehiclemanagement.vehicle.admin.dto.VehicleTrimDTO(t.idx, t.drivetrain, t.fuelType, t.transmission) " +
            "FROM VehicleTrimEntity t " +
            "WHERE t.model.idx = :modelIdx")
    List<VehicleTrimDTO> findAllDTOListByModelIdx(Long modelIdx);
}
