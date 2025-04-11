package gimeast.vehiclemanagement.vehicle.admin.dto;

import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleTrimEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTrimDTO {
    private Long idx;
    private String drivetrain;
    private String fuelType;
    private String transmission;

    public VehicleTrimDTO(VehicleTrimEntity trimEntity) {
        this.idx = trimEntity.getIdx();
        this.drivetrain = trimEntity.getDrivetrain();
        this.fuelType = trimEntity.getFuelType();
        this.transmission = trimEntity.getTransmission();
    }

    public VehicleTrimEntity toEntity() {
        return VehicleTrimEntity.builder()
                .idx(idx)
                .drivetrain(drivetrain)
                .fuelType(fuelType)
                .transmission(transmission)
                .build();
    }
}
