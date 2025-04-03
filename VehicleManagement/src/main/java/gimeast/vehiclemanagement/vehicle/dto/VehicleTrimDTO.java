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
    private String engineType;
    private String transmission;

    public VehicleTrimEntity toEntity() {
        return VehicleTrimEntity.builder()
                .idx(idx)
                .drivetrain(drivetrain)
                .engineType(engineType)
                .transmission(transmission)
                .build();
    }
}
