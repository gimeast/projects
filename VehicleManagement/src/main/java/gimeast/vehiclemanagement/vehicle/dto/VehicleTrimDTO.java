package gimeast.vehiclemanagement.vehicle.dto;

import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTrimDTO {
    private Long idx;
    private VehicleModelDTO modelDTO;
    private String drivetrain;
    private String fuelType;
    private String transmission;

    public VehicleTrimEntity toEntity() {
        return VehicleTrimEntity.builder()
                .idx(idx)
                .drivetrain(drivetrain)
                .fuelType(fuelType)
                .transmission(transmission)
                .build();
    }
}
