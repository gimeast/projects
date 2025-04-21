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

    public VehicleModelDTO(VehicleModelEntity entity) {
        this.idx = entity.getIdx();
        this.name = entity.getName();
    }

    public VehicleModelEntity toEntity() {
        return VehicleModelEntity.builder()
                .name(name)
                .build();
    }
}
