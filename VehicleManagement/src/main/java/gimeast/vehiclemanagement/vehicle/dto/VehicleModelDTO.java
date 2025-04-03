package gimeast.vehiclemanagement.vehicle.dto;

import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModelDTO {
    private Long idx;
    private String name;
    private String year;
    private VehicleBrandDTO brandDTO;

    public VehicleModelEntity toEntity() {
        return VehicleModelEntity.builder()
                .name(name)
                .year(year)
                .build();
    }
}
